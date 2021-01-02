/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.gui.renderer.Region;

public class WVerticalSeparator extends WWidget {
    @Override
    protected void onCalculateSize(GuiRenderer renderer) {
        width = 1;
        height = 0;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(Region.FULL, x, y, width, height, GuiConfig.INSTANCE.separator);
    }
}
