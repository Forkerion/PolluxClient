/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.settings;

import rardoger.polluxclient.gui.screens.settings.BlockSettingScreen;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WItem;
import rardoger.polluxclient.gui.widgets.WTable;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class BlockSetting extends Setting<Block> {
    private final WItem itemWidget;

    public BlockSetting(String name, String description, Block defaultValue, Consumer<Block> onChanged, Consumer<Setting<Block>> onModuleActivated) {
        super(name, description, defaultValue, onChanged, onModuleActivated);

        WTable table = new WTable();
        itemWidget = table.add(new WItem(get().asItem().getDefaultStack())).getWidget();
        table.add(new WButton("Select")).getWidget().action = () -> MinecraftClient.getInstance().openScreen(new BlockSettingScreen(this));

        widget = table;
    }

    @Override
    protected Block parseImpl(String str) {
        Identifier id;
        if (str.contains(":")) id = new Identifier(str);
        else id = new Identifier("minecraft", str);

        return Registry.BLOCK.get(id);
    }

    @Override
    public void resetWidget() {
        itemWidget.set(get().asItem().getDefaultStack());
    }

    @Override
    protected boolean isValueValid(Block value) {
        return true;
    }

    @Override
    protected String generateUsage() {
        return "(highlight)block id (default)(dirt, minecraft:stone, etc)";
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putString("value", Registry.BLOCK.getId(get()).toString());

        return tag;
    }

    @Override
    public Block fromTag(CompoundTag tag) {
        value = Registry.BLOCK.get(new Identifier(tag.getString("value")));

        changed();
        return get();
    }

    public static class Builder {
        private String name = "undefined", description = "";
        private Block defaultValue;
        private Consumer<Block> onChanged;
        private Consumer<Setting<Block>> onModuleActivated;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder defaultValue(Block defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder onChanged(Consumer<Block> onChanged) {
            this.onChanged = onChanged;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Block>> onModuleActivated) {
            this.onModuleActivated = onModuleActivated;
            return this;
        }

        public BlockSetting build() {
            return new BlockSetting(name, description, defaultValue, onChanged, onModuleActivated);
        }
    }
}
