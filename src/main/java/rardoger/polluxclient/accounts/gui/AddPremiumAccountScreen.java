/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts.gui;

import rardoger.polluxclient.accounts.types.PremiumAccount;
import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WTextBox;

public class AddPremiumAccountScreen extends WindowScreen {
    public AddPremiumAccountScreen() {
        super("Add Premium Account", true);

        // Email
        add(new WLabel("Email:"));
        WTextBox email = add(new WTextBox("", 400)).getWidget();
        email.setFocused(true);
        row();

        // Password
        add(new WLabel("Password:"));
        WTextBox password = add(new WTextBox("", 400)).getWidget();
        row();

        // Add
        WButton add = add(new WButton("Add")).fillX().expandX().getWidget();
        add.action = () -> {
            if (!email.getText().isEmpty() && !password.getText().isEmpty() && email.getText().contains("@")) {
                AccountsScreen.addAccount(add, this, new PremiumAccount(email.getText(), password.getText()));
            }
        };
    }
}
