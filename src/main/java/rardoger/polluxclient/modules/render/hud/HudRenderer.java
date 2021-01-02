/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud;

import rardoger.polluxclient.rendering.Fonts;
import rardoger.polluxclient.rendering.MyFont;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.render.color.Color;

public class HudRenderer {
    private MyFont font;
    private double scale;

    public void setScale(double scale) {
        double scaleA = Math.floor(scale * 10) / 10;

        int scaleI;
        if (scaleA >= 3) scaleI = 5;
        else if (scaleA >= 2.5) scaleI = 4;
        else if (scaleA >= 2) scaleI = 3;
        else if (scaleA >= 1.5) scaleI = 2;
        else scaleI = 1;

        font = Fonts.get(scaleI - 1);

        this.scale = (scale - (((scaleI - 1) * 0.5) + 1)) / scaleA + 1;
    }

    public void begin(double scale) {
        setScale(scale);

        Utils.unscaledProjection();
        font.begin(this.scale);
    }

    public void end() {
        font.end();
        Utils.scaledProjection();
    }

    public void text(String text, double x, double y, Color color) {
        font.renderWithShadow(text, x, y, color);
    }

    public double textWidth(String text) {
        return font.getWidth(text) * scale;
    }

    public double textHeight() {
        return font.getHeight() * scale;
    }
}
