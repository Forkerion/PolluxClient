/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class LiquidInteract extends ToggleModule {
    public LiquidInteract() {
        super(Category.Player, "liquid-interact", "Allows you to interact with liquids.");
    }
}
