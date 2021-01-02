/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.modules.render.hud.HUD;
import rardoger.polluxclient.modules.render.hud.HudEditorScreen;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.render.color.Color;
import rardoger.polluxclient.utils.world.TickRate;
import net.minecraft.client.MinecraftClient;

public class LagNotifierHud extends DoubleTextHudModule {
    private static final Color RED = new Color(225, 45, 45);
    private static final Color AMBER = new Color(235, 158, 52);
    private static final Color YELLOW = new Color(255, 255, 5);

    public LagNotifierHud(HUD hud) {
        super(hud, "lag-notifier", "Displays if the server is lagging in ticks.", "Time since last tick ");
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) {
            rightColor = RED;
            visible = true;
            return "4,3";
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        float timeSinceLastTick = TickRate.INSTANCE.getTimeSinceLastTick();

        if (timeSinceLastTick > 10) rightColor = RED;
        else if (timeSinceLastTick > 3) rightColor = AMBER;
        else rightColor = YELLOW;

        visible = timeSinceLastTick >= 1f || mc.currentScreen instanceof HudEditorScreen;
        return String.format("%.1f", timeSinceLastTick);
    }
}
