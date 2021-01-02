/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient;

import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.accounts.AccountManager;
import rardoger.polluxclient.commands.CommandManager;
import rardoger.polluxclient.commands.commands.Ignore;
import rardoger.polluxclient.events.game.GameLeftEvent;
import rardoger.polluxclient.events.pollux.KeyEvent;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.friends.FriendManager;
import rardoger.polluxclient.gui.screens.topbar.TopBarModules;
import rardoger.polluxclient.macros.MacroManager;
import rardoger.polluxclient.mixininterface.IKeyBinding;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.rendering.Fonts;
import rardoger.polluxclient.rendering.MFont;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.entity.EntityUtils;
import rardoger.polluxclient.utils.misc.input.KeyAction;
import rardoger.polluxclient.utils.misc.input.KeyBinds;
import rardoger.polluxclient.utils.network.Capes;
import rardoger.polluxclient.utils.network.PolluxExecutor;
import rardoger.polluxclient.utils.network.OnlinePlayers;
import rardoger.polluxclient.utils.player.EChestMemory;
import rardoger.polluxclient.utils.render.color.RainbowColorManager;
import rardoger.polluxclient.utils.world.BlockIterator;
import rardoger.polluxclient.waypoints.Waypoints;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;

import java.io.File;

public class PolluxClient implements ClientModInitializer, Listenable {
    public static PolluxClient INSTANCE;
    public static final EventBus EVENT_BUS = new EventManager();
    public static MFont FONT_2X;
    public static boolean IS_DISCONNECTING;
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "pollux-client");

    private MinecraftClient mc;

    public Screen screenToOpen;

    @Override
    public void onInitializeClient() {
        if (INSTANCE == null) {
            KeyBinds.Register();

            INSTANCE = this;
            return;
        }

        System.out.println("Initializing Pollux Client.");

        mc = MinecraftClient.getInstance();
        Utils.mc = mc;
        EntityUtils.mc = mc;

        Config.INSTANCE = new Config();
        Config.INSTANCE.load();
        Fonts.init();

        PolluxExecutor.init();
        new ModuleManager();
        CommandManager.init();
        EChestMemory.init();
        Capes.init();
        BlockIterator.init();
        RainbowColorManager.init();

        load();
        Ignore.load();
        Waypoints.loadIcons();

        EVENT_BUS.subscribe(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            save();
            OnlinePlayers.leave();
        }));
    }

    public void load() {
        if (!ModuleManager.INSTANCE.load()) {
            Utils.addPolluxPvpToServerList();
        }

        FriendManager.INSTANCE.load();
        MacroManager.INSTANCE.load();
        AccountManager.INSTANCE.load();
    }

    public void save() {
        System.out.println("[Pollux] Saving");

        Config.INSTANCE.save();
        ModuleManager.INSTANCE.save();
        FriendManager.INSTANCE.save();
        MacroManager.INSTANCE.save();
        AccountManager.INSTANCE.save();

        Ignore.save();
    }

    private void openClickGui() {
        mc.openScreen(new TopBarModules());
    }

    @EventHandler
    private final Listener<GameLeftEvent> onGameLeft = new Listener<>(event -> save());

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        Capes.tick();

        if (screenToOpen != null && mc.currentScreen == null) {
            mc.openScreen(screenToOpen);
            screenToOpen = null;
        }

        if (Utils.canUpdate()) {
            mc.player.getActiveStatusEffects().values().removeIf(statusEffectInstance -> statusEffectInstance.getDuration() <= 0);
        }
    });

    @EventHandler
    private final Listener<KeyEvent> onKey = new Listener<>(event -> {
        // Click GUI
        if (event.action == KeyAction.Press && event.key == KeyBindingHelper.getBoundKeyOf(KeyBinds.OPEN_CLICK_GUI).getCode()) {
            if (!Utils.canUpdate() || mc.currentScreen == null) openClickGui();
        }

        // Shulker Peek
        KeyBinding shulkerPeek = KeyBinds.SHULKER_PEEK;
        ((IKeyBinding) shulkerPeek).setPressed(shulkerPeek.matchesKey(event.key, 0) && event.action != KeyAction.Release);
    });

    public static <T> T postEvent(T event) {
        EVENT_BUS.post(event);
        return event;
    }
}
