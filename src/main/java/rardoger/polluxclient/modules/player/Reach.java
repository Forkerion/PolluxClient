/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.DoubleSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;

public class Reach extends ToggleModule {
    private final SettingGroup sg = settings.getDefaultGroup();

    private final Setting<Double> reach = sg.add(new DoubleSetting.Builder()
            .name("reach")
            .description("Your reach.")
            .defaultValue(5)
            .min(0)
            .sliderMax(6)
            .build()
    );

    public Reach() {
        super(Category.Player, "reach", "Gives you super long arms.");
    }

    public float getReach() {
        return reach.get().floatValue();
    }
}
