/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.movement;

import baritone.api.BaritoneAPI;
import com.google.common.collect.Streams;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.*;
import rardoger.polluxclient.utils.player.DamageCalcUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;

import java.util.Comparator;
import java.util.Optional;

public class Step extends ToggleModule {
    public enum ActiveWhen {
        Always,
        Sneaking,
        NotSneaking
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    
    public final Setting<Double> height = sgGeneral.add(new DoubleSetting.Builder()
            .name("height")
            .description("Step height.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    private final Setting<ActiveWhen> activeWhen = sgGeneral.add(new EnumSetting.Builder<ActiveWhen>()
            .name("active-when")
            .description("Step is active when you meet these requirements.")
            .defaultValue(ActiveWhen.Always)
            .build()
    );

    private final Setting<Boolean> safeStep = sgGeneral.add(new BoolSetting.Builder()
            .name("Safe-step")
            .description("Doesn't let you step out of a hole if you are low on health or there is a crystal nearby.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> stepHealth = sgGeneral.add(new IntSetting.Builder()
            .name("step-health")
            .description("The health you stop being able to step at.")
            .defaultValue(5)
            .min(1)
            .max(36)
            .build()
    );

    private float prevStepHeight;
    private boolean prevBaritoneAssumeStep;

    public Step() {
        super(Category.Movement, "step", "Allows you to walk up full blocks normally.");
    }

    @Override
    public void onActivate() {
        assert mc.player != null;
        prevStepHeight = mc.player.stepHeight;

        prevBaritoneAssumeStep = BaritoneAPI.getSettings().assumeStep.value;
        BaritoneAPI.getSettings().assumeStep.value = true;
    }

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        assert mc.player != null;
        boolean work = (activeWhen.get() == ActiveWhen.Always) || (activeWhen.get() == ActiveWhen.Sneaking && mc.player.isSneaking()) || (activeWhen.get() == ActiveWhen.NotSneaking && !mc.player.isSneaking());
        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, 1, 0));
        if (work && (!safeStep.get() || (getHealth() > stepHealth.get() && getHealth() - getExplosionDamage() > stepHealth.get()))){
            mc.player.stepHeight = height.get().floatValue();
        } else {
            mc.player.stepHeight = prevStepHeight;
        }
        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, -1, 0));
    });

    @Override
    public void onDeactivate() {
        assert mc.player != null;
        mc.player.stepHeight = prevStepHeight;

        BaritoneAPI.getSettings().assumeStep.value = prevBaritoneAssumeStep;
    }

    private float getHealth(){
        assert mc.player != null;
        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
    }

    private double getExplosionDamage(){
        assert mc.player != null;
        assert mc.world != null;
        Optional<EndCrystalEntity> crystal = Streams.stream(mc.world.getEntities())
                .filter(entity -> entity instanceof EndCrystalEntity)
                .filter(Entity::isAlive)
                .max(Comparator.comparingDouble(o -> DamageCalcUtils.crystalDamage(mc.player, o.getPos())))
                .map(entity -> (EndCrystalEntity) entity);
        return crystal.map(endCrystalEntity -> DamageCalcUtils.crystalDamage(mc.player, endCrystalEntity.getPos())).orElse(0.0);
    }
}
