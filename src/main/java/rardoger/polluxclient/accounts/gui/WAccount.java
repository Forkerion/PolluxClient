/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts.gui;

import rardoger.polluxclient.accounts.Account;
import rardoger.polluxclient.accounts.AccountManager;
import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.gui.widgets.*;
import rardoger.polluxclient.utils.network.PolluxExecutor;
import net.minecraft.client.MinecraftClient;

public class WAccount extends WTable {
    public WAccount(AccountsScreen screen, Account<?> account) {
        // Head
        add(new WTexture(16, 16, 90, account.getCache().getHeadTexture()));

        // Name
        WLabel name = add(new WLabel(account.getUsername())).getWidget();
        if (MinecraftClient.getInstance().getSession().getUsername().equalsIgnoreCase(account.getUsername())) name.color = GuiConfig.INSTANCE.loggedInText;

        // Account Type
        WLabel type = add(new WLabel("(" + account.getType() + ")")).fillX().right().getWidget();
        type.color = GuiConfig.INSTANCE.accountTypeText;

        // Login
        WButton login = add(new WButton("Login")).getWidget();
        login.action = () -> {
            login.freezeWidth();
            login.setText("...");
            screen.locked = true;

            PolluxExecutor.execute(() -> {
                if (account.login()) {
                    name.setText(account.getUsername());

                    AccountManager.INSTANCE.save();

                    screen.clear();
                    screen.initWidgets();
                }

                login.setText("Login");
                screen.locked = false;
            });
        };

        // Remove
        WMinus minus = add(new WMinus()).getWidget();
        minus.action = () -> AccountManager.INSTANCE.remove(account);
    }
}
