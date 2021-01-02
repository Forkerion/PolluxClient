/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.modules.render.hud.modules;

import rardoger.polluxclient.modules.render.hud.BoundingBox;
import rardoger.polluxclient.modules.render.hud.HUD;
import rardoger.polluxclient.modules.render.hud.HudRenderer;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.misc.ISerializable;
import net.minecraft.nbt.CompoundTag;

public abstract class HudModule implements ISerializable<HudModule> {
    public final String name, title;
    public final String description;

    public boolean active = true;

    protected final HUD hud;

    public final BoundingBox box = new BoundingBox();

    public HudModule(HUD hud, String name, String description) {
        this.hud = hud;
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
    }

    public abstract void update(HudRenderer renderer);

    public abstract void render(HudRenderer renderer);

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putString("name", name);
        tag.putBoolean("active", active);
        tag.put("box", box.toTag());

        return tag;
    }

    @Override
    public HudModule fromTag(CompoundTag tag) {
        active = tag.getBoolean("active");
        box.fromTag(tag.getCompound("box"));

        return this;
    }
}
