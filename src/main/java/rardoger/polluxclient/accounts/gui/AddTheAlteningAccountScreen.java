/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts.gui;

import rardoger.polluxclient.accounts.types.TheAlteningAccount;
import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WTextBox;

public class AddTheAlteningAccountScreen extends WindowScreen {
    public AddTheAlteningAccountScreen() {
        super("Add The Altening Account", true);

        // Token
        add(new WLabel("Token:"));
        WTextBox token = add(new WTextBox("", 400)).getWidget();
        token.setFocused(true);
        row();

        // Add
        WButton add = add(new WButton("Add")).fillX().expandX().getWidget();
        add.action = () -> {
            if (!token.getText().isEmpty()) {
                AccountsScreen.addAccount(add, this, new TheAlteningAccount(token.getText()));
            }
        };
    }
}
