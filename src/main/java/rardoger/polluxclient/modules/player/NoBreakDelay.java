/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class NoBreakDelay extends ToggleModule {
    public NoBreakDelay() {
        super(Category.Player, "no-break-delay", "Completely removes the delay between breaking blocks.");
    }
}