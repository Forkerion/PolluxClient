/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.events.entity.player.ClipAtLedgeEvent;
import rardoger.polluxclient.mixininterface.IPlayerEntity;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.movement.Anchor;
import rardoger.polluxclient.modules.player.SpeedMine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntity {
    @Shadow @Final @Mutable public PlayerInventory inventory;

    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    protected void clipAtLedge(CallbackInfoReturnable<Boolean> info) {
        ClipAtLedgeEvent event = PolluxClient.postEvent(EventStore.clipAtLedgeEvent());
        if (event.isSet()) info.setReturnValue(event.isClip());
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    private void onDropItem(ItemStack stack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> info) {
        PolluxClient.EVENT_BUS.post(EventStore.dropItemEvent(stack));
    }

    @Override
    public void setInventory(PlayerInventory inventory) {
        this.inventory = inventory;
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "RETURN"), cancellable = true)
    public void onGetBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        SpeedMine module = ModuleManager.INSTANCE.get(SpeedMine.class);
        if (!module.isActive() || module.mode.get() != SpeedMine.Mode.Normal)
            return;
        cir.setReturnValue((float) (cir.getReturnValue() * module.modifier.get()));
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void dontJump(CallbackInfo info) {
        Anchor module = ModuleManager.INSTANCE.get(Anchor.class);
        if (module.isActive() && module.cancelJump) info.cancel();
    }
}
