/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public interface IVec3d {
    void set(double x, double y, double z);

    default void set(Vec3d vec) {
        set(vec.x, vec.y, vec.z);
    }
    default void set(Vec3i vec) {
        set(vec.getX(), vec.getY(), vec.getZ());
    }
    void setXZ(double x, double z);

    void setY(double y);
}
