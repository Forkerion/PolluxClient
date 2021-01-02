/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.events.game.OpenScreenEvent;
import rardoger.polluxclient.gui.GuiKeyEvents;
import rardoger.polluxclient.gui.WidgetScreen;
import rardoger.polluxclient.mixininterface.IMinecraftClient;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.player.AutoEat;
import rardoger.polluxclient.modules.player.AutoGap;
import rardoger.polluxclient.utils.network.OnlinePlayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.net.Proxy;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    @Shadow public ClientWorld world;

    @Shadow private int itemUseCooldown;

    @Shadow protected abstract void doItemUse();

    @Shadow protected abstract void doAttack();

    @Shadow public Mouse mouse;

    @Shadow private Window window;

    @Shadow @Final private Proxy netProxy;

    @Shadow private Session session;

    @Shadow private static int currentFps;

    @Shadow @Nullable public Screen currentScreen;

    @Shadow public abstract Profiler getProfiler();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        PolluxClient.INSTANCE.onInitializeClient();
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onPreTick(CallbackInfo info) {
        OnlinePlayers.update();

        getProfiler().push("pollux-client_pre_update");
        PolluxClient.EVENT_BUS.post(EventStore.preTickEvent());
        getProfiler().pop();
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        getProfiler().push("pollux-client_post_update");
        PolluxClient.EVENT_BUS.post(EventStore.postTickEvent());
        getProfiler().pop();
    }
    
    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconnect(Screen screen, CallbackInfo info) {
        if (world != null) PolluxClient.EVENT_BUS.post(EventStore.gameLeftEvent());
    }

    @Inject(method = "openScreen", at = @At("HEAD"), cancellable = true)
    private void onOpenScreen(Screen screen, CallbackInfo info) {
        if (screen instanceof WidgetScreen) screen.mouseMoved(mouse.getX() * window.getScaleFactor(), mouse.getY() * window.getScaleFactor());

        OpenScreenEvent event = EventStore.openScreenEvent(screen);
        PolluxClient.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            info.cancel();
            return;
        }

        GuiKeyEvents.resetPostKeyEvents();
    }

    @Redirect(method = "doItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", ordinal = 1))
    private HitResult doItemUseMinecraftClientCrosshairTargetProxy(MinecraftClient client) {
        if (ModuleManager.INSTANCE.get(AutoEat.class).rightClickThings() && ModuleManager.INSTANCE.get(AutoGap.class).rightClickThings()) return client.crosshairTarget;
        return null;
    }

    @Override
    public void leftClick() {
        doAttack();
    }

    @Override
    public void rightClick() {
        doItemUse();
    }

    @Override
    public void setItemUseCooldown(int cooldown) {
        itemUseCooldown = cooldown;
    }

    @Override
    public Proxy getProxy() {
        return netProxy;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public int getFps() {
        return currentFps;
    }
}
