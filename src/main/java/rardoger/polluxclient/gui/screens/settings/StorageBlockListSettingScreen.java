/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WCheckbox;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.StorageBlockListSetting;
import net.minecraft.block.entity.BlockEntityType;

import java.util.List;

public class StorageBlockListSettingScreen extends WindowScreen {
    public StorageBlockListSettingScreen(Setting<List<BlockEntityType<?>>> setting) {
        super("Select storage blocks", true);

        for (int i = 0; i < StorageBlockListSetting.STORAGE_BLOCKS.length; i++) {
            BlockEntityType<?> type = StorageBlockListSetting.STORAGE_BLOCKS[i];
            String name = StorageBlockListSetting.STORAGE_BLOCK_NAMES[i];

            add(new WLabel(name));
            WCheckbox checkbox = add(new WCheckbox(setting.get().contains(type))).fillX().right().getWidget();
            checkbox.action = () -> {
                if (checkbox.checked && !setting.get().contains(type)) {
                    setting.get().add(type);
                    setting.changed();
                } else if (!checkbox.checked && setting.get().remove(type)) {
                    setting.changed();
                }
            };

            row();
        }
    }
}
