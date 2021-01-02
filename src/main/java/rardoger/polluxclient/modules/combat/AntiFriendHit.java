/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.combat;

//Created by squidoodly 16/07/2020 (Yay! Empty class!!!)

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.entity.player.AttackEntityEvent;
import rardoger.polluxclient.friends.FriendManager;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.entity.player.PlayerEntity;

public class AntiFriendHit extends ToggleModule {
    public AntiFriendHit() {
        super(Category.Combat, "anti-friend-hit", "Cancels out attacks that would hit friends.");
    }

    @EventHandler
    private final Listener<AttackEntityEvent> onAttackEntity = new Listener<>(event -> {
        if (event.entity instanceof PlayerEntity &&  ModuleManager.INSTANCE.get(AntiFriendHit.class).isActive() && !FriendManager.INSTANCE.attack((PlayerEntity) event.entity)) event.cancel();
    });
}
