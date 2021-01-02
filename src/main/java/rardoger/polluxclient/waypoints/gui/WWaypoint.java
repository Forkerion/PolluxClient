/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.waypoints.gui;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalGetToBlock;
import rardoger.polluxclient.gui.widgets.*;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.render.color.Color;
import rardoger.polluxclient.utils.world.Dimension;
import rardoger.polluxclient.waypoints.Waypoint;
import rardoger.polluxclient.waypoints.Waypoints;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class WWaypoint extends WTable {
    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color GRAY = new Color(200, 200, 200);

    public WWaypoint(Waypoint waypoint) {
        // Icon
        add(new WWaypointIcon(waypoint));

        // Name
        WLabel name = add(new WLabel(waypoint.name)).getWidget();
        boolean goodDimension = false;
        Dimension dimension = Utils.getDimension();
        if (waypoint.overworld && dimension == Dimension.Overworld) goodDimension = true;
        else if (waypoint.nether && dimension == Dimension.Nether) goodDimension = true;
        else if (waypoint.end && dimension == Dimension.End) goodDimension = true;
        name.color = goodDimension ? WHITE : GRAY;

        // Visible, edit, remove
        WTable right = add(new WTable()).fillX().right().getWidget();
        WCheckbox visible = right.add(new WCheckbox(waypoint.visible)).getWidget();
        visible.action = () -> {
            waypoint.visible = visible.checked;
            Waypoints.INSTANCE.save();
        };
        right.add(new WButton(WButton.ButtonRegion.Edit)).getWidget().action = () -> MinecraftClient.getInstance().openScreen(new EditWaypointScreen(waypoint));
        right.add(new WMinus()).getWidget().action = () -> Waypoints.INSTANCE.remove(waypoint);
        WButton path = new WButton("Goto");
        path.action = () -> {
            if(MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().world == null) return;
            IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
            if (baritone.getPathingBehavior().isPathing())
                baritone.getPathingBehavior().cancelEverything();
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(new BlockPos(waypoint.x, waypoint.y, waypoint.z)));
        };
        right.add(path);
    }
}
