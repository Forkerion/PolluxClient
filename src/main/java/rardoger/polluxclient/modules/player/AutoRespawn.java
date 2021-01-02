/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.game.OpenScreenEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends ToggleModule {
    public AutoRespawn() {
        super(Category.Player, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler
    private final Listener<OpenScreenEvent> onOpenScreenEvent = new Listener<>(event -> {
        if (!(event.screen instanceof DeathScreen)) return;

        mc.player.requestRespawn();
        event.cancel();
    });
}
