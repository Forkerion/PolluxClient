/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import com.g00fy2.versioncompare.Version;
import rardoger.polluxclient.Config;
import rardoger.polluxclient.gui.screens.NewUpdateScreen;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.network.HttpUtils;
import rardoger.polluxclient.utils.network.PolluxExecutor;
import rardoger.polluxclient.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private int text1Color;
    private int text2Color;
    private int text3Color;
    private int text4Color;

    private String text1;
    private int text1Length;

    private String text2;
    private int text2Length;

    private String text3;
    private int text3Length;

    private String text4;
    private int text4Length;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        text1Color = Color.fromRGBA(255, 255, 255, 255);
        text2Color = Color.fromRGBA(175, 175, 175, 255);
        text3Color = Color.fromRGBA(255, 255, 255, 255);
        text4Color = Color.fromRGBA(175, 175, 175, 255);

        text1 = "Pollux Client by ";
        text2 = "RarDoGer";
        text3 = "  ";
        text4 = "";

        text1Length = textRenderer.getWidth(text1);
        text2Length = textRenderer.getWidth(text2);
        text3Length = textRenderer.getWidth(text3);
        text4Length = textRenderer.getWidth(text4);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0))
    private void onRenderIdkDude(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Utils.firstTimeTitleScreen) {
            Utils.firstTimeTitleScreen = false;
            System.out.println("Checking latest version of Pollux Client.");

            PolluxExecutor.execute(() -> HttpUtils.getLines("http://polluxclient.com:8082/api/version", s -> {
                Version latestVer = new Version(s);
                if (latestVer.isHigherThan(Config.INSTANCE.version)) MinecraftClient.getInstance().openScreen(new NewUpdateScreen(latestVer));
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        textRenderer.drawWithShadow(matrices, text1, width - text4Length - text3Length - text2Length - text1Length - 3, 3, text1Color);
        textRenderer.drawWithShadow(matrices, text2, width - text4Length - text3Length - text2Length - 3, 3, text2Color);
        textRenderer.drawWithShadow(matrices, text3, width - text4Length - text3Length - 3, 3, text3Color);
        textRenderer.drawWithShadow(matrices, text4, width - text4Length - 3, 3, text4Color);
    }
}
