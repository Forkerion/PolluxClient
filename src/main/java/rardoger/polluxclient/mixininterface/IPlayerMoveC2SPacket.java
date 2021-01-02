/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

public interface IPlayerMoveC2SPacket {
    void setY(double y);
    void setOnGround(boolean onGround);
    void setYaw(float yaw);
    void setPitch(float pitch);
}
