/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.player;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class AntiCactus extends ToggleModule {
    public AntiCactus() {
        super(Category.Player, "anti-cactus", "Prevents you from taking damage from cacti.");
    }
}
