/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.entity.player.JumpVelocityMultiplierEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.DoubleSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;

public class HighJump extends ToggleModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    
    private final Setting<Double> multiplier = sgGeneral.add(new DoubleSetting.Builder()
            .name("multiplier")
            .description("Jump height multiplier.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    public HighJump() {
        super(Category.Movement, "high-jump", "Makes you jump higher than normal.");
    }

    @EventHandler
    private final Listener<JumpVelocityMultiplierEvent> onJumpVelocityMultiplier = new Listener<>(event -> {
        event.multiplier *= multiplier.get();
    });
}
