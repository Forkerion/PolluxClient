/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.utils.render.color.Color;

public class WTriangle extends WPressable {
    public Color color, colorHovered, colorPressed;

    public double rotation;

    public WTriangle() {
        color = GuiConfig.INSTANCE.background;
        colorHovered = GuiConfig.INSTANCE.backgroundHovered;
        colorPressed = GuiConfig.INSTANCE.backgroundPressed;
    }

    @Override
    protected void onCalculateSize(GuiRenderer renderer) {
        width = height = renderer.textHeight();
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        Color color;
        if (pressed) color = colorPressed;
        else if (mouseOver) color = colorHovered;
        else color = this.color;

        renderer.triangle(x, y + width / 4, width, rotation, color);
    }
}
