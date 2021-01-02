/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.Config;
import rardoger.polluxclient.modules.render.hud.HUD;

public class WatermarkHud extends DoubleTextHudModule {
    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a Pollux Client watermark.", "Pollux Client ");
    }

    @Override
    protected String getRight() {
        return Config.INSTANCE.version.getOriginalString();
    }
}
