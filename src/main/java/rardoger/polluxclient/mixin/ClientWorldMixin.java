/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.render.Search;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
        PolluxClient.EVENT_BUS.post(EventStore.entityAddedEvent(entity));
    }

    @Inject(method = "finishRemovingEntity", at = @At("TAIL"))
    private void onFinishRemovingEntity(Entity entity, CallbackInfo info) {
        PolluxClient.EVENT_BUS.post(EventStore.entityRemovedEvent(entity));
    }

    @Inject(method = "setBlockStateWithoutNeighborUpdates", at = @At("TAIL"))
    private void onSetBlockStateWithoutNeighborUpdates(BlockPos blockPos, BlockState blockState, CallbackInfo info) {
        Search search = ModuleManager.INSTANCE.get(Search.class);
        if (search.isActive()) search.onBlockUpdate(blockPos, blockState);
    }
}
