/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WIntTextBox;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WTable;
import rardoger.polluxclient.gui.widgets.WTextBox;
import rardoger.polluxclient.settings.Setting;
import net.minecraft.entity.effect.StatusEffect;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatusEffectSettingScreen extends WindowScreen {
    private final Setting<Object2IntMap<StatusEffect>> setting;
    private final WTextBox filter;

    private String filterText = "";

    public StatusEffectSettingScreen(Setting<Object2IntMap<StatusEffect>> setting) {
        super("Select Potions", true);

        this.setting = setting;

        // Filter
        filter = new WTextBox("", 200);
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.getText().trim();

            clear();
            initWidgets();
        };

        initWidgets();
    }

    private void initWidgets() {
        add(filter).fillX().expandX();
        row();

        List<StatusEffect> statusEffects = new ArrayList<>(setting.get().keySet());
        statusEffects.sort(Comparator.comparing(statusEffect -> statusEffect.getName().getString()));

        WTable table = add(new WTable()).expandX().fillX().getWidget();

        for (StatusEffect statusEffect : statusEffects) {
            String name = statusEffect.getName().getString();
            if (!StringUtils.containsIgnoreCase(name, filterText)) continue;

            table.add(new WLabel(name));
            WIntTextBox level = table.add(new WIntTextBox(setting.get().getInt(statusEffect), 50)).fillX().right().getWidget();
            level.action = () -> {
                setting.get().put(statusEffect, level.getValue());
                setting.changed();
            };

            table.row();
        }
    }
}
