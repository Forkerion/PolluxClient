/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.IDisconnectedScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin implements IDisconnectedScreen {
    @Shadow @Final private Screen parent;

    @Shadow @Final private Text reason;

    @Shadow private int reasonHeight;

    @Override
    public Screen getParent() {
        return parent;
    }

    @Override
    public Text getReason() {
        return reason;
    }

    @Override
    public int getReasonHeight() {
        return reasonHeight;
    }
}
