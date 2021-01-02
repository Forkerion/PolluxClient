/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;

public class AutoSprint extends ToggleModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> permanent = sgGeneral.add(new BoolSetting.Builder()
            .name("permanent")
            .description("Makes you still sprint even if you do not move.")
            .defaultValue(true)
            .build()
    );
	
    public AutoSprint() {
        super(Category.Movement, "auto-sprint", "Automatically sprints.");
    }
    
    @Override
    public void onDeactivate() {
        mc.player.setSprinting(false);
    }

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
    	if(mc.player.forwardSpeed > 0 && !permanent.get()) {
            mc.player.setSprinting(true);
    	} else if (permanent.get()) {
    		mc.player.setSprinting(true);
    	}
    });
}
