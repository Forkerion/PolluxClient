/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.friends.FriendManager;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.modules.player.FakePlayer;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.DoubleSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.entity.FakePlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AutoWeb extends ToggleModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
            .name("range")
            .description("The maximum distance to be able to place webs.")
            .defaultValue(4)
            .min(0)
            .build()
    );

    private final Setting<Boolean> doubles = sgGeneral.add(new BoolSetting.Builder()
            .name("doubles")
            .description("Places in the target's upper hitbox as well as the lower hitbox.")
            .defaultValue(false)
            .build()
    );

    public AutoWeb() {
        super(Category.Combat, "auto-web", "Automatically places webs on other players.");
    }

    private PlayerEntity target = null;

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        int webSlot = -1;
        for (int i = 0; i < 9; i++) {
            Item item = mc.player.inventory.getStack(i).getItem();

            if (item == Items.COBWEB) {
                webSlot = i;
                break;
            }
        }
        if (webSlot == -1) return;

        if (target != null) {
            if (mc.player.distanceTo(target) > range.get() || !target.isAlive()) target = null;
        }

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player || !FriendManager.INSTANCE.attack(player) || !player.isAlive() || mc.player.distanceTo(player) > range.get())
                continue;
            if (target == null) {
                target = player;
            } else if (mc.player.distanceTo(target) > mc.player.distanceTo(player)) {
                target = player;
            }
        }

        if (target == null) {
            for (FakePlayerEntity fakeTarget : FakePlayer.players.keySet()) {
                if (fakeTarget.getHealth() <= 0 || !FriendManager.INSTANCE.attack(fakeTarget) || !fakeTarget.isAlive()) continue;

                if (target == null) {
                    target = fakeTarget;
                    continue;
                }

                if (mc.player.distanceTo(fakeTarget) < mc.player.distanceTo(target)) target = fakeTarget;
            }
        }

        if (target != null) {
            int prevSlot = mc.player.inventory.selectedSlot;
            mc.player.inventory.selectedSlot = webSlot;
            BlockPos targetPos = target.getBlockPos();
            int swung = 0;
            if (mc.world.getBlockState(targetPos).isAir()) {
                mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.DOWN, targetPos, true));
                swung++;
            }
            if (doubles.get() && mc.world.getBlockState(targetPos.add(0, 1, 0)).isAir()) {
                mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.UP, targetPos.add(0, 1, 0), true));
                swung++;
            }
            if (swung >= 1) mc.player.swingHand(Hand.MAIN_HAND);
            mc.player.inventory.selectedSlot = prevSlot;
        }
    });
}