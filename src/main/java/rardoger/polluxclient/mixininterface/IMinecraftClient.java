/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixininterface;

import net.minecraft.client.util.Session;

import java.net.Proxy;

public interface IMinecraftClient {
    void leftClick();

    void rightClick();

    void setItemUseCooldown(int cooldown);

    Proxy getProxy();

    void setSession(Session session);

    int getFps();
}
