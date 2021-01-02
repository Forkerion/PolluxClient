/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.utils.render.color.Color;

public class WTitle extends WWidget {
    public Color color;

    private final String text;

    public WTitle(String text) {
        this.text = text;

        this.color = GuiConfig.INSTANCE.text;
    }

    @Override
    protected void onCalculateSize(GuiRenderer renderer) {
        width = renderer.titleWidth(text);
        height = renderer.titleHeight();
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.title(text, x, y, color);
    }
}
