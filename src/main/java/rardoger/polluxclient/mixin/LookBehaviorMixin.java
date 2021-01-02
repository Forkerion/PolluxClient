/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import baritone.api.utils.Rotation;
import baritone.behavior.LookBehavior;
import rardoger.polluxclient.mixininterface.ILookBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = LookBehavior.class, remap = false)
public class LookBehaviorMixin implements ILookBehavior {
    @Shadow private Rotation target;

    @Override
    public Rotation getTarget() {
        return target;
    }
}
