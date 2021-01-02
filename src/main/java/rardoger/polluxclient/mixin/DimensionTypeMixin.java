/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.IDimensionType;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DimensionType.class)
public class DimensionTypeMixin implements IDimensionType {
    @Shadow @Final protected static DimensionType THE_NETHER;

    @Shadow @Final protected static DimensionType THE_END;

    @Override
    public DimensionType getNether() {
        return THE_NETHER;
    }

    @Override
    public DimensionType getEnd() {
        return THE_END;
    }
}
