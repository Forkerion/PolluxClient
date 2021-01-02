/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.settings.StringSetting;

public class NameProtect extends ToggleModule {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> name = sgGeneral.add(new StringSetting.Builder()
            .name("name")
            .description("Name to be replaced with.")
            .defaultValue("squidoodly")
            .build()
    );

    public NameProtect() {
        super(Category.Player, "name-protect", "Hides your name client-side.");
    }

    private String username = "If you see this, something is wrong.";

    @Override
    public void onActivate() {
        username = mc.getSession().getUsername();
    }

    public String replaceName(String string) {
        if (string.contains(username) && name.get().length() > 0 && isActive()) {
            return string.replace(username, name.get());
        } else return string;
    }

    public String getName(String original) {
        if (name.get().length() > 0 && isActive()) return name.get();
        else return original;
    }
}