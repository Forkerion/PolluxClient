/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.misc;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class EChestPreview extends ToggleModule {
    public EChestPreview() {
        super(Category.Misc, "EChest-preview", "Stores what's inside your Ender Chests and displays when you hover over it.");
    }
}
