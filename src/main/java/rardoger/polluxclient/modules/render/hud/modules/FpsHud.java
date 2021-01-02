/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.mixininterface.IMinecraftClient;
import rardoger.polluxclient.modules.render.hud.HUD;
import net.minecraft.client.MinecraftClient;

public class FpsHud extends DoubleTextHudModule {
    public FpsHud(HUD hud) {
        super(hud, "fps", "Displays your FPS.", "Fps: ");
    }

    @Override
    protected String getRight() {
        return Integer.toString(((IMinecraftClient) MinecraftClient.getInstance()).getFps());
    }
}
