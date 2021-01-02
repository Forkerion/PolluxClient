/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.utils.misc.input;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    private static final String CATEGORY = "Pollux Client";

    public static KeyBinding OPEN_CLICK_GUI = new KeyBinding("key.pollux-client.open-click-gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY);
    public static KeyBinding SHULKER_PEEK = new KeyBinding("key.pollux-client.shulker-peek", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, CATEGORY);
    public static KeyBinding MENU = new KeyBinding("key.pollux-client.open-menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, CATEGORY);


    public static void Register() {
        KeyBindingHelper.registerKeyBinding(OPEN_CLICK_GUI);
        KeyBindingHelper.registerKeyBinding(SHULKER_PEEK);
        KeyBindingHelper.registerKeyBinding(MENU);
    }
}
