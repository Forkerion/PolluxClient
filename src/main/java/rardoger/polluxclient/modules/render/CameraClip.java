/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class CameraClip extends ToggleModule {
    public CameraClip() {
        super(Category.Render, "camera-clip", "Allows your third person camera to clip through blocks.");
    }
}
