/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.settings.Setting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class EnchListSettingScreen extends LeftRightListSettingScreen<Enchantment> {
    public EnchListSettingScreen(Setting<List<Enchantment>> setting) {
        super("Select items", setting, Registry.ENCHANTMENT);
    }

    @Override
    protected WWidget getValueWidget(Enchantment value) {
        return new WLabel(value.getName(1).getString());
    }

    @Override
    protected String getValueName(Enchantment value) {
        return value.getName(1).getString();
    }
}
