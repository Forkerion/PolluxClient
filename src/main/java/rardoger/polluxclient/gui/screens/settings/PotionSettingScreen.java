/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.*;
import rardoger.polluxclient.settings.PotionSetting;
import rardoger.polluxclient.utils.misc.MyPotion;
import org.apache.commons.lang3.StringUtils;

public class PotionSettingScreen extends WindowScreen {
    private final PotionSetting setting;
    private final WTextBox filter;

    private String filterText = "";

    public PotionSettingScreen(PotionSetting setting) {
        super("Select Potion", true);

        this.setting = setting;

        // Filter
        filter = new WTextBox("", 400);
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.getText().trim();

            clear();
            initWidgets();
        };

        initWidgets();
    }

    private void initWidgets() {
        add(filter).fillX().expandX().getWidget();
        row();

        // Potions
        WTable table = add(new WTable()).getWidget();
        for (MyPotion potion : MyPotion.values()) {
            WItemWithLabel item = new WItemWithLabel(potion.potion);
            if (!filterText.isEmpty()) {
                if (!StringUtils.containsIgnoreCase(item.getLabelText(), filterText)) continue;
            }
            table.add(item);

            WButton select = table.add(new WButton("Select")).getWidget();
            select.action = () -> {
                setting.set(potion);
                onClose();
            };
            table.add(new WHorizontalSeparator());
        }
    }
}
