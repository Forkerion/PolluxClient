/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class UnfocusedCPU extends ToggleModule {
    public UnfocusedCPU() {
        super(Category.Render, "unfocused-CPU", "Will not render anything when your Minecraft window is not focused.");
    }
}