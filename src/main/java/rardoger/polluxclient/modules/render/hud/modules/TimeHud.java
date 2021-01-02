/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.modules.render.hud.HUD;
import net.minecraft.client.MinecraftClient;

public class TimeHud extends DoubleTextHudModule {
    public TimeHud(HUD hud) {
        super(hud, "time", "Displays the world time.", "Time: ");
    }

    @Override
    protected String getRight() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null) return "00:00";

        int ticks = (int) (mc.world.getTimeOfDay() % 24000);
        ticks += 6000;
        if (ticks > 24000) ticks -= 24000;
        return String.format("%02d:%02d", ticks / 1000, (int) (ticks % 1000 / 1000.0 * 60));
    }
}
