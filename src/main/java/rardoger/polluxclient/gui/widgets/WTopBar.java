/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.widgets;

import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.renderer.GuiRenderer;
import rardoger.polluxclient.gui.renderer.Region;
import rardoger.polluxclient.gui.screens.topbar.TopBarScreen;
import rardoger.polluxclient.gui.screens.topbar.TopBarType;
import rardoger.polluxclient.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

public class WTopBar extends WTable {
    public WTopBar() {
        defaultCell.space(0);

        for (TopBarType type : TopBarType.values()) {
            add(new WTopBarButton(type));
        }
    }

    private static class WTopBarButton extends WPressable {
        private final TopBarType type;
        private final String name;

        private WTopBarButton(TopBarType type) {
            this.type = type;
            this.name = type.toString();
        }

        @Override
        protected void onCalculateSize(GuiRenderer renderer) {
            width = 4 + renderer.textWidth(name) + 4;
            height = 4 + renderer.textHeight() + 4;
        }

        @Override
        protected void onAction(int button) {
            Screen screen = MinecraftClient.getInstance().currentScreen;

            if (!(screen instanceof TopBarScreen) || ((TopBarScreen) screen).type != type) {
                MinecraftClient mc = MinecraftClient.getInstance();

                double mouseX = mc.mouse.getX();
                double mouseY = mc.mouse.getY();

                if (screen != null) screen.onClose();
                mc.openScreen(type.createScreen());

                GLFW.glfwSetCursorPos(mc.getWindow().getHandle(), mouseX, mouseY);
            }
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            Color color = GuiConfig.INSTANCE.background;
            if (pressed) color = GuiConfig.INSTANCE.backgroundPressed;
            else if (mouseOver) color = GuiConfig.INSTANCE.backgroundHovered;

            Screen screen = MinecraftClient.getInstance().currentScreen;
            if (screen instanceof TopBarScreen && ((TopBarScreen) screen).type == type) color = GuiConfig.INSTANCE.backgroundPressed;

            renderer.quad(Region.FULL, x, y, width, height, color);
            renderer.text(name, x + 4, y + 4, false, GuiConfig.INSTANCE.text);
        }
    }
}
