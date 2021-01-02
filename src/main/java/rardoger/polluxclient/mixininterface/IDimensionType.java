/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

import net.minecraft.world.dimension.DimensionType;

public interface IDimensionType {
    DimensionType getNether();

    DimensionType getEnd();
}
