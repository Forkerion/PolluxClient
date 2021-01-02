/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.widgets.WItemWithLabel;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.settings.Setting;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ItemListSettingScreen extends LeftRightListSettingScreen<Item> {
    public ItemListSettingScreen(Setting<List<Item>> setting) {
        super("Select items", setting, Registry.ITEM);
    }

    @Override
    protected boolean includeValue(Item value) {
        return value != Items.AIR;
    }

    @Override
    protected WWidget getValueWidget(Item value) {
        return new WItemWithLabel(value.getDefaultStack());
    }

    @Override
    protected String getValueName(Item value) {
        return value.getName().getString();
    }
}
