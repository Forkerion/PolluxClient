/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;

public class WModuleCategory extends WWindow {
    public WModuleCategory(Category category) {
        super(category.toString(), GuiConfig.INSTANCE.getWindowConfig(get(category)).isExpanded(), true);
        type = get(category);

        action = () -> GuiConfig.INSTANCE.getWindowConfig(type).setPos(x, y);

        pad(0);
        getDefaultCell().space(0);

        for (Module module : ModuleManager.INSTANCE.getGroup(category)) {
            if (!(module instanceof ToggleModule)) continue;

            add(new WModule((ToggleModule) module)).fillX().expandX();
            row();
        }
    }

    private static GuiConfig.WindowType get(Category category) {
        switch (category) {
            case Combat:   return GuiConfig.WindowType.Combat;
            case Player:   return GuiConfig.WindowType.Player;
            case Movement: return GuiConfig.WindowType.Movement;
            case Render:   return GuiConfig.WindowType.Render;
            case Misc:     return GuiConfig.WindowType.Misc;
        }

        return null;
    }
}
