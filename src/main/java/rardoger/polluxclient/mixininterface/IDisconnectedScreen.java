/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public interface IDisconnectedScreen {
    Screen getParent();

    Text getReason();

    int getReasonHeight();
}
