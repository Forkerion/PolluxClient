/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.misc;

import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;

public class AntiPacketKick extends ToggleModule {
    public AntiPacketKick() {
        super(Category.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
    }
}
