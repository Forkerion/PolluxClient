/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.events.packets;

import rardoger.polluxclient.events.Cancellable;
import net.minecraft.network.Packet;

public class SendPacketEvent extends Cancellable {
    public Packet<?> packet;
}
