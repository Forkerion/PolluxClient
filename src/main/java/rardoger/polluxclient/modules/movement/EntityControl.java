/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

//Created by squidoodly 10/07/2020

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.mixininterface.IHorseBaseEntity;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;

public class EntityControl extends ToggleModule {
    public EntityControl(){super(Category.Movement, "entity-control", "Lets you control rideable entities without a saddle.");}

    @Override
    public void onDeactivate() {
        mc.world.getEntities().forEach(entity -> {
            if (entity instanceof HorseBaseEntity) {
                ((IHorseBaseEntity) entity).setSaddled(false);
            }
        });
    }

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        mc.world.getEntities().forEach(entity -> {
            if (entity instanceof HorseBaseEntity) {
                ((IHorseBaseEntity) entity).setSaddled(true);
            }
        });

        Entity entity = mc.player.getVehicle();
        if (entity == null) return;

        if (entity instanceof HorseBaseEntity) {
            ((HorseBaseEntity) entity).setAiDisabled(true);
            ((HorseBaseEntity) entity).setTame(true);
        }
    });
}
