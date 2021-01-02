/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.settings;

import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SoundEventListSettingScreen extends LeftRightListSettingScreen<SoundEvent> {
    public SoundEventListSettingScreen(Setting<List<SoundEvent>> setting) {
        super("Select sounds", setting, Registry.SOUND_EVENT);
    }

    @Override
    protected WWidget getValueWidget(SoundEvent value) {
        return new WLabel(getValueName(value));
    }

    @Override
    protected String getValueName(SoundEvent value) {
        WeightedSoundSet soundSet = MinecraftClient.getInstance().getSoundManager().get(value.getId());
        if (soundSet == null) return value.getId().getPath();

        Text text = soundSet.getSubtitle();
        if (text == null) return value.getId().getPath();

        return text.getString();
    }
}
