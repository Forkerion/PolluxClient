/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.waypoints.gui;

import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.waypoints.Waypoint;

public class WWaypointIcon extends WWidget {
    private final Waypoint waypoint;

    public WWaypointIcon(Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    protected void onCalculateSize(GuiRenderer renderer) {
        width = 32;
        height = 32;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.post(() -> waypoint.renderIcon(x, y, 0, 1, 32));
    }
}
