/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.IAbstractFurnaceScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractFurnaceScreenHandler.class)
public abstract class AbstractFurnaceScreenHandlerMixin implements IAbstractFurnaceScreenHandler {
    @Shadow protected abstract boolean isSmeltable(ItemStack itemStack);

    @Shadow protected abstract boolean isFuel(ItemStack itemStack);

    @Override
    public boolean isSmeltableI(ItemStack itemStack) {
        return isSmeltable(itemStack);
    }

    @Override
    public boolean isFuelI(ItemStack itemStack) {
        return isFuel(itemStack);
    }
}
