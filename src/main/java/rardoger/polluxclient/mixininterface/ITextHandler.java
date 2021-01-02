/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

import net.minecraft.client.font.TextHandler;

public interface ITextHandler {
    TextHandler.WidthRetriever getWidthRetriever();
}
