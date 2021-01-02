/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.topbar;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.pollux.WaypointListChangedEvent;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.waypoints.Waypoint;
import rardoger.polluxclient.waypoints.Waypoints;
import rardoger.polluxclient.waypoints.gui.EditWaypointScreen;
import rardoger.polluxclient.waypoints.gui.WWaypoint;
import net.minecraft.client.MinecraftClient;

public class TopBarWaypoints extends TopBarWindowScreen {
    public TopBarWaypoints() {
        super(TopBarType.Waypoints);
    }

    @Override
    protected void initWidgets() {
        // Waypoints
        for (Waypoint waypoint : Waypoints.INSTANCE) {
            add(new WWaypoint(waypoint)).fillX().expandX();
            row();
        }

        // Add
        if (Utils.canUpdate()) {
            WButton add = add(new WButton("Add")).fillX().expandX().getWidget();
            add.action = () -> MinecraftClient.getInstance().openScreen(new EditWaypointScreen(null));
        }
    }

    @EventHandler
    private final Listener<WaypointListChangedEvent> onWaypointListChanged = new Listener<>(event -> {
        clear();
        initWidgets();
    });
}
