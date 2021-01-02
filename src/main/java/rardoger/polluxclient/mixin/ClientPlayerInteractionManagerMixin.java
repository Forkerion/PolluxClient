/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.events.entity.player.AttackEntityEvent;
import rardoger.polluxclient.events.entity.player.StartBreakingBlockEvent;
import rardoger.polluxclient.mixininterface.IClientPlayerInteractionManager;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.misc.Nuker;
import rardoger.polluxclient.modules.player.NoBreakDelay;
import rardoger.polluxclient.modules.player.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {
    @Shadow protected abstract void syncSelectedSlot();

    @Shadow private int blockBreakingCooldown;

    @Shadow private float currentBreakingProgress;

    @Shadow private BlockPos currentBreakingPos;

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
        AttackEntityEvent event = EventStore.attackEntityEvent(target);
        PolluxClient.EVENT_BUS.post(event);

        if (event.isCancelled()) info.cancel();
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        StartBreakingBlockEvent event = EventStore.startBreakingBlockEvent(blockPos, direction);
        PolluxClient.EVENT_BUS.post(event);

        if (event.isCancelled()) info.cancel();
    }

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> info) {
        if (ModuleManager.INSTANCE.isActive(Reach.class)) info.setReturnValue(ModuleManager.INSTANCE.get(Reach.class).getReach());
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD))
    private void onMethod_2902SetField_3716Proxy(ClientPlayerInteractionManager interactionManager, int value) {
        if (ModuleManager.INSTANCE.isActive(Nuker.class)) value = 0;
        if (ModuleManager.INSTANCE.isActive(NoBreakDelay.class)) value = 0;
        blockBreakingCooldown = value;
    }

    @Redirect(method = "attackBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD))
    private void onAttackBlockSetField_3719Proxy(ClientPlayerInteractionManager interactionManager, int value) {
        if (ModuleManager.INSTANCE.isActive(Nuker.class)) value = 0;
        blockBreakingCooldown = value;
    }

    @Inject(method = "breakBlock", at = @At("HEAD"))
    private void onBreakBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> info) {
        PolluxClient.EVENT_BUS.post(EventStore.breakBlockEvent(blockPos));
    }

    @Override
    public void syncSelectedSlot2() {
        syncSelectedSlot();
    }

    @Override
    public double getBreakingProgress() {
        return currentBreakingProgress;
    }

    @Override
    public BlockPos getCurrentBreakingBlockPos() {
        return currentBreakingPos;
    }
}
