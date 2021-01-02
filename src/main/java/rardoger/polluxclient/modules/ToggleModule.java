/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules;

import rardoger.polluxclient.Config;
import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Formatting;

public abstract class ToggleModule extends Module {
    private boolean active;
    private boolean visible = true;

    public ToggleModule(Category category, String name, String description) {
        super(category, name, description);
    }

    public void onActivate() {}
    public void onDeactivate() {}

    public void toggle(boolean onActivateDeactivate) {
        if (!active) {
            active = true;
            ModuleManager.INSTANCE.addActive(this);

            for (SettingGroup sg : settings) {
                for (Setting setting : sg) {
                    if (setting.onModuleActivated != null) setting.onModuleActivated.accept(setting);
                }
            }

            if (onActivateDeactivate) {
                PolluxClient.EVENT_BUS.subscribe(this);
                onActivate();
            }
        }
        else {
            active = false;
            ModuleManager.INSTANCE.removeActive(this);

            if (onActivateDeactivate) {
                PolluxClient.EVENT_BUS.unsubscribe(this);
                onDeactivate();
            }
        }
    }
    public void toggle() {
        toggle(true);
    }

    @Override
    public void doAction(boolean onActivateDeactivate) {
        toggle(onActivateDeactivate);
    }

    public String getInfoString() {
        return null;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = super.toTag();

        tag.putBoolean("active", active);
        tag.putBoolean("visible", visible);

        return tag;
    }

    @Override
    public ToggleModule fromTag(CompoundTag tag) {
        super.fromTag(tag);

        boolean active = tag.getBoolean("active");
        if (active != isActive()) toggle(Utils.canUpdate());
        setVisible(tag.getBoolean("visible"));

        return this;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        PolluxClient.EVENT_BUS.post(EventStore.moduleVisibilityChangedEvent(this));
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isActive() {
        return active;
    }

    public void sendToggledMsg() {
        if (Config.INSTANCE.chatCommandsInfo) Chat.info("Toggled (highlight)%s(default) %s(default).", title, isActive() ? Formatting.GREEN + "on" : Formatting.RED + "off");
    }
}
