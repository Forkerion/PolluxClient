/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.modules.render.hud.HUD;
import rardoger.polluxclient.utils.world.TickRate;

public class TpsHud extends DoubleTextHudModule {
    public TpsHud(HUD hud) {
        super(hud, "tps", "Displays the server's TPS.", "Tps: ");
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", TickRate.INSTANCE.getTickRate());
    }
}
