/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.macros;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.utils.files.Savable;
import rardoger.polluxclient.utils.misc.NbtUtils;
import net.minecraft.nbt.CompoundTag;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MacroManager extends Savable<MacroManager> implements Iterable<Macro> {
    public static final MacroManager INSTANCE = new MacroManager();

    private List<Macro> macros = new ArrayList<>();

    private MacroManager() {
        super(new File(PolluxClient.FOLDER, "macros.nbt"));
    }

    public void add(Macro macro) {
        macros.add(macro);
        PolluxClient.EVENT_BUS.subscribe(macro);
        PolluxClient.EVENT_BUS.post(EventStore.macroListChangedEvent());
        save();
    }

    public List<Macro> getAll() {
        return macros;
    }

    public void remove(Macro macro) {
        if (macros.remove(macro)) {
            PolluxClient.EVENT_BUS.unsubscribe(macro);
            PolluxClient.EVENT_BUS.post(EventStore.macroListChangedEvent());
            save();
        }
    }

    @Override
    public Iterator<Macro> iterator() {
        return macros.iterator();
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("macros", NbtUtils.listToTag(macros));
        return tag;
    }

    @Override
    public MacroManager fromTag(CompoundTag tag) {
        for (Macro macro : macros) PolluxClient.EVENT_BUS.unsubscribe(macro);

        macros = NbtUtils.listFromTag(tag.getList("macros", 10), tag1 -> new Macro().fromTag((CompoundTag) tag1));

        for (Macro macro : macros) PolluxClient.EVENT_BUS.subscribe(macro);
        return this;
    }
}
