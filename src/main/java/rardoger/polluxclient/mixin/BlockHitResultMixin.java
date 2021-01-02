/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.IBlockHitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockHitResult.class)
public class BlockHitResultMixin implements IBlockHitResult {
    @Shadow private Direction side;

    @Override
    public void setSide(Direction direction) {
        this.side = direction;
    }
}
