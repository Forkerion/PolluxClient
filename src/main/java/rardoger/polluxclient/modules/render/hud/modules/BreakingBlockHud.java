/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.mixininterface.IClientPlayerInteractionManager;
import rardoger.polluxclient.modules.render.hud.HUD;
import net.minecraft.client.MinecraftClient;

public class BreakingBlockHud extends DoubleTextHudModule {
    public BreakingBlockHud(HUD hud) {
        super(hud, "breaking-block", "Displays percentage of the block you are breaking.", "Breaking Block: ");
    }

    @Override
    protected String getRight() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.interactionManager == null) return "0%";

        return String.format("%.0f%%", ((IClientPlayerInteractionManager) mc.interactionManager).getBreakingProgress() * 100);
    }
}
