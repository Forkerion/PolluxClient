/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.IMiningToolItem;
import net.minecraft.block.Block;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin implements IMiningToolItem {
    @Shadow @Final private Set<Block> effectiveBlocks;

    @Override
    public boolean isEffectiveOn(Block block) {
        return effectiveBlocks.contains(block);
    }
}
