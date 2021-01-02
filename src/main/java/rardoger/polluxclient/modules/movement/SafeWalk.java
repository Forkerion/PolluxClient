/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.entity.player.ClipAtLedgeEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class SafeWalk extends ToggleModule {
    public SafeWalk() {
        super(Category.Movement, "Safe-walk", "Prevents you from walking off blocks. Useful over a void.");
    }

    @EventHandler
    private final Listener<ClipAtLedgeEvent> onClipAtLedge = new Listener<>(event -> {
        event.setClip(true);
    });
}
