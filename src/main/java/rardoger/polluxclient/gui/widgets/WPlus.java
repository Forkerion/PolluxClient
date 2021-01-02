/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.gui.renderer.Region;
import rardoger.polluxclient.utils.render.color.Color;

public class WPlus extends WPressable {
    @Override
    protected void onCalculateSize(GuiRenderer renderer) {
        width = 6 + renderer.textHeight() + 6;
        height = 6 + renderer.textHeight() + 6;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.background(this, mouseOver, pressed);

        Color color = GuiConfig.INSTANCE.plus;
        if (pressed) color = GuiConfig.INSTANCE.plusPressed;
        else if (mouseOver) color = GuiConfig.INSTANCE.plusHovered;

        renderer.quad(Region.FULL, x + 7, y + 6 + renderer.textHeight() / 2 - 1, renderer.textHeight() - 1, 3, color);
        renderer.quad(Region.FULL, x + 6 + renderer.textHeight() / 2 - 1, y + 7, 3, renderer.textHeight() - 1, color);
    }
}
