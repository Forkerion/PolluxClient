/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.topbar;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.pollux.MacroListChangedEvent;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WMinus;
import rardoger.polluxclient.gui.widgets.WTable;
import rardoger.polluxclient.macros.EditMacroScreen;
import rardoger.polluxclient.macros.Macro;
import rardoger.polluxclient.macros.MacroManager;
import rardoger.polluxclient.utils.Utils;
import net.minecraft.client.MinecraftClient;

public class TopBarMacros extends TopBarWindowScreen {
    public TopBarMacros() {
        super(TopBarType.Macros);
    }

    @Override
    protected void initWidgets() {
        // Macros
        if (MacroManager.INSTANCE.getAll().size() > 0) {
            WTable t = add(new WTable()).getWidget();

            for (Macro macro : MacroManager.INSTANCE) {
                t.add(new WLabel(macro.name + " (" + Utils.getKeyName(macro.key) + ")"));

                WButton edit = t.add(new WButton(WButton.ButtonRegion.Edit)).getWidget();
                edit.action = () -> MinecraftClient.getInstance().openScreen(new EditMacroScreen(macro));

                WMinus remove = t.add(new WMinus()).getWidget();
                remove.action = () -> MacroManager.INSTANCE.remove(macro);
            }

            row();
        }

        // Create macro
        WButton create = add(new WButton("Create")).fillX().expandX().getWidget();
        create.action = () -> MinecraftClient.getInstance().openScreen(new EditMacroScreen(null));
    }

    @EventHandler
    private final Listener<MacroListChangedEvent> onMacroListChanged = new Listener<>(event -> {
        clear();
        initWidgets();
    });
}
