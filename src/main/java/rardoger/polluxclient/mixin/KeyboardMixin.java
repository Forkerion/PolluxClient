/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.events.pollux.CharTypedEvent;
import rardoger.polluxclient.events.pollux.KeyEvent;
import rardoger.polluxclient.gui.GuiKeyEvents;
import rardoger.polluxclient.gui.WidgetScreen;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.misc.input.Input;
import rardoger.polluxclient.utils.misc.input.KeyAction;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo info) {
        if (key != GLFW.GLFW_KEY_UNKNOWN) {
            if (client.currentScreen instanceof WidgetScreen && i == GLFW.GLFW_REPEAT) {
                ((WidgetScreen) client.currentScreen).keyRepeated(key, j);
            }

            if (GuiKeyEvents.postKeyEvents()) {
                Input.setKeyState(key, i != GLFW.GLFW_RELEASE);

                KeyEvent event = EventStore.keyEvent(key, KeyAction.get(i));
                PolluxClient.EVENT_BUS.post(event);

                if (event.isCancelled()) info.cancel();
            }
        }
    }

    @Inject(method = "onChar", at = @At("HEAD"), cancellable = true)
    private void onChar(long window, int i, int j, CallbackInfo info) {
        if (Utils.canUpdate() && !client.isPaused() && (client.currentScreen == null || client.currentScreen instanceof WidgetScreen)) {
            CharTypedEvent event = EventStore.charTypedEvent((char) i);
            PolluxClient.EVENT_BUS.post(event);

            if (event.isCancelled()) info.cancel();
        }
    }
}
