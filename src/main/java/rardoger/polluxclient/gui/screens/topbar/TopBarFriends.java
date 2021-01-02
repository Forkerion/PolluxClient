/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.topbar;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.pollux.FriendListChangedEvent;
import rardoger.polluxclient.friends.EditFriendScreen;
import rardoger.polluxclient.friends.Friend;
import rardoger.polluxclient.friends.FriendManager;
import rardoger.polluxclient.gui.widgets.*;
import net.minecraft.client.MinecraftClient;

public class TopBarFriends extends TopBarWindowScreen {
    public TopBarFriends() {
        super(TopBarType.Friends);
    }

    @Override
    protected void initWidgets() {
        // Friends
        for (Friend friend : FriendManager.INSTANCE) {
            add(new WLabel(friend.name));
            add(new WButton(WButton.ButtonRegion.Edit)).getWidget().action = () -> MinecraftClient.getInstance().openScreen(new EditFriendScreen(friend));

            WMinus remove = add(new WMinus()).getWidget();
            remove.action = () -> FriendManager.INSTANCE.remove(friend);

            row();
        }

        // Add friend
        WTable t = add(new WTable()).fillX().expandX().getWidget();
        WTextBox username = t.add(new WTextBox("", 400)).fillX().expandX().getWidget();
        username.setFocused(true);

        WPlus add = t.add(new WPlus()).getWidget();
        add.action = () -> {
            String name = username.getText().trim();
            if (!name.isEmpty()) FriendManager.INSTANCE.add(new Friend(name));
        };
    }

    @EventHandler
    private final Listener<FriendListChangedEvent> onFriendListChanged = new Listener<>(event -> {
        clear();
        initWidgets();
    });
}
