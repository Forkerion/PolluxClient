/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache;
import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.events.render.DrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockOcclusionCache.class, remap = false)
public class BlockOcculsionCacheMixin {
    @Inject(at = @At("HEAD"), method = "shouldDrawSide", cancellable = true)
    private void shouldDrawSide(BlockState selfState, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> info) {
        DrawSideEvent event = PolluxClient.postEvent(EventStore.drawSideEvent(selfState));
        if (event.isSet()) info.setReturnValue(event.getDraw());
    }
}
