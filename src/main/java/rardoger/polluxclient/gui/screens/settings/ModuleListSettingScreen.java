/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.Setting;

import java.util.List;

public class ModuleListSettingScreen extends LeftRightListSettingScreen<ToggleModule> {
    public ModuleListSettingScreen(Setting<List<ToggleModule>> setting) {
        super("Select Modules", setting, ModuleManager.REGISTRY);
    }

    @Override
    protected WWidget getValueWidget(ToggleModule value) {
        return new WLabel(value.title);
    }

    @Override
    protected String getValueName(ToggleModule value) {
        return value.title;
    }
}
