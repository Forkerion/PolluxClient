/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts.gui;

import rardoger.polluxclient.accounts.types.CrackedAccount;
import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WTextBox;

public class AddCrackedAccountScreen extends WindowScreen {
    public AddCrackedAccountScreen() {
        super("Add Cracked Account", true);

        // Name
        add(new WLabel("Name:"));
        WTextBox name = add(new WTextBox("", 400)).getWidget();
        name.setFocused(true);
        row();

        // Add
        WButton add = add(new WButton("Add")).fillX().expandX().getWidget();
        add.action = () -> {
            if (!name.getText().isEmpty()) {
                AccountsScreen.addAccount(add, this, new CrackedAccount(name.getText()));
            }
        };
    }
}
