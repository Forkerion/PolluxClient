/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.pollux.KeyEvent;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.modules.render.Freecam;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.misc.input.KeyAction;

public class AirJump extends ToggleModule {
    public AirJump() {
        super(Category.Movement, "air-jump", "Lets you jump in the air.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> maintainY = sgGeneral.add(new BoolSetting.Builder()
            .name("maintain-level")
            .description("Maintains your current Y level")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> onHold = sgGeneral.add(new BoolSetting.Builder()
            .name("on-hold")
            .description("Whether or not to air jump if you hold down the space bar.")
            .defaultValue(true)
            .build()
    );

    private int level = 0;

    @EventHandler
    private final Listener<KeyEvent> onKey = new Listener<>(event -> {
        if (ModuleManager.INSTANCE.isActive(Freecam.class) || mc.currentScreen != null) return;
        if ((event.action == KeyAction.Press || (event.action == KeyAction.Repeat && onHold.get())) && mc.options.keyJump.matchesKey(event.key, 0)) {
            mc.player.jump();
            level = mc.player.getBlockPos().getY();
        }
        if ((event.action == KeyAction.Press || (event.action == KeyAction.Repeat && onHold.get())) && mc.options.keySneak.matchesKey(event.key, 0)){
            level -= 1;
        }
    });

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        if (ModuleManager.INSTANCE.isActive(Freecam.class)) return;
        if (maintainY.get() && mc.player.getBlockPos().getY() == level){
            mc.player.jump();
        }
    });
}
