/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.mixininterface.IKeyBinding;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.IntSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.player.InvUtils;
import net.minecraft.item.Items;

public class BowSpam extends ToggleModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> charge = sgGeneral.add(new IntSetting.Builder()
            .name("charge")
            .description("How long to charge the bow before releasing in ticks.")
            .defaultValue(5)
            .min(5)
            .max(20)
            .sliderMin(5)
            .sliderMax(20)
            .build()
    );

    private final Setting<Boolean> onlyWhenHoldingRightClick = sgGeneral.add(new BoolSetting.Builder()
            .name("only-when-holding-right-click")
            .description("Works only when holding right click.")
            .defaultValue(false)
            .build()
    );

    private boolean wasBow = false;
    private boolean wasHoldingRightClick = false;

    public BowSpam() {
        super(Category.Combat, "bow-spam", "Spams arrows.");
    }

    @Override
    public void onActivate() {
        wasBow = false;
        wasHoldingRightClick = false;
    }

    @Override
    public void onDeactivate() {
        setPressed(false);
    }

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        assert mc.player != null;
        assert mc.interactionManager != null;
        if (InvUtils.findItemWithCount(Items.ARROW).slot == -1) return;
        if (!onlyWhenHoldingRightClick.get() || mc.options.keyUse.isPressed()) {
            boolean isBow = mc.player.getMainHandStack().getItem() == Items.BOW;
            if (!isBow && wasBow) setPressed(false);

            wasBow = isBow;
            if (!isBow) return;

            if (mc.player.getItemUseTime() >= charge.get()) {
                mc.player.stopUsingItem();
                mc.interactionManager.stopUsingItem(mc.player);
            } else {
                setPressed(true);
            }

            wasHoldingRightClick = mc.options.keyUse.isPressed();
        } else {
            if (wasHoldingRightClick) {
                setPressed(false);
                wasHoldingRightClick = false;
            }
        }
    });

    private void setPressed(boolean pressed) {
        ((IKeyBinding) mc.options.keyUse).setPressed(pressed);
    }
}
