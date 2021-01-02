package rardoger.polluxclient.utils.render.color;

import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.waypoints.Waypoint;
import rardoger.polluxclient.waypoints.Waypoints;

import java.util.ArrayList;
import java.util.List;

public class RainbowColorManager {
    private static final List<Setting<SettingColor>> colorSettings = new ArrayList<>();
    private static final List<SettingColor> colors = new ArrayList<>();

    public static void init() {
        PolluxClient.EVENT_BUS.subscribe(onTick);
    }

    public static void addColorSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void addColor(SettingColor color) {
        colors.add(color);
    }

    private static final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isActive()) setting.get().update();
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.INSTANCE) {
            waypoint.color.update();
        }
    });
}
