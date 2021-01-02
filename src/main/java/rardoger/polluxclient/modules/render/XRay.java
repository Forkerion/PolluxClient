/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.Cancellable;
import rardoger.polluxclient.events.render.DrawSideEvent;
import rardoger.polluxclient.events.render.RenderBlockEntityEvent;
import rardoger.polluxclient.events.world.AmbientOcclusionEvent;
import rardoger.polluxclient.events.world.ChunkOcclusionEvent;
import rardoger.polluxclient.mixin.BlockEntityTypeAccessor;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.BlockListSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.Utils;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class XRay extends ToggleModule {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    
    private final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
            .name("blocks")
            .description("Blocks.")
            .defaultValue(new ArrayList<>(0))
            .onChanged(blocks1 -> {
                if (isActive()) mc.worldRenderer.reload();
            })
            .build()
    );

    private boolean fullBrightWasActive = false;

    public XRay() {
        super(Category.Render, "xray", "Only renders specified blocks. Good for mining.");
    }

    @Override
    public void onActivate() {
        FullBright fullBright = ModuleManager.INSTANCE.get(FullBright.class);
        fullBrightWasActive = fullBright.isActive();
        if (!fullBright.isActive()) fullBright.toggle();

        mc.worldRenderer.reload();
    }

    @Override
    public void onDeactivate() {
        FullBright fullBright = ModuleManager.INSTANCE.get(FullBright.class);
        if (!fullBrightWasActive && fullBright.isActive()) fullBright.toggle();

        if (!PolluxClient.IS_DISCONNECTING) mc.worldRenderer.reload();
    }

    @EventHandler
    private final Listener<RenderBlockEntityEvent> onRenderBlockEntity = new Listener<>(event -> {
        if (!Utils.blockRenderingBlockEntitiesInXray) return;

        for (Block block : ((BlockEntityTypeAccessor) event.blockEntity.getType()).getBlocks()) {
            if (isBlocked(block)) {
                event.cancel();
                break;
            }
        }
    });

    @EventHandler
    private final Listener<DrawSideEvent> onDrawSide = new Listener<>(event -> event.setDraw(!isBlocked(event.state.getBlock())));

    @EventHandler
    private final Listener<ChunkOcclusionEvent> onChunkOcclusion = new Listener<>(Cancellable::cancel);

    @EventHandler
    private final Listener<AmbientOcclusionEvent> onAmbientOcclusion = new Listener<>(event -> event.lightLevel = 1);

    public boolean isBlocked(Block block) {
        return isActive() && !blocks.get().contains(block);
    }
}
