/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts.gui;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.accounts.Account;
import rardoger.polluxclient.accounts.AccountManager;
import rardoger.polluxclient.events.pollux.AccountListChangedEvent;
import rardoger.polluxclient.gui.WidgetScreen;
import rardoger.polluxclient.gui.screens.WindowScreen;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WTable;
import rardoger.polluxclient.utils.network.PolluxExecutor;
import net.minecraft.client.MinecraftClient;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen() {
        super("Accounts", true);

        initWidgets();
    }

    void initWidgets() {
        // Accounts
        if (AccountManager.INSTANCE.size() > 0) {
            WTable t = add(new WTable()).fillX().expandX().getWidget();
            row();

            for (Account<?> account : AccountManager.INSTANCE) {
                t.add(new WAccount(this, account)).fillX().expandX();
                t.row();
            }
        }

        // Add account
        WTable t = add(new WTable()).fillX().expandX().getWidget();
        addButton(t, "Cracked", () -> MinecraftClient.getInstance().openScreen(new AddCrackedAccountScreen()));
        addButton(t, "Premium", () -> MinecraftClient.getInstance().openScreen(new AddPremiumAccountScreen()));
        addButton(t, "The Altening", () -> MinecraftClient.getInstance().openScreen(new AddTheAlteningAccountScreen()));
    }

    private void addButton(WTable t, String text, Runnable action) {
        WButton button = t.add(new WButton(text)).fillX().expandX().getWidget();
        button.action = action;
    }

    @EventHandler
    private final Listener<AccountListChangedEvent> onAccountListChanged = new Listener<>(event -> {
        clear();
        initWidgets();
    });

    static void addAccount(WButton add, WidgetScreen screen, Account<?> account) {
        add.setText("...");
        screen.locked = true;

        PolluxExecutor.execute(() -> {
            if (account.fetchInfo() && account.fetchHead()) {
                AccountManager.INSTANCE.add(account);
                screen.locked = false;
                screen.onClose();
            }

            add.setText("Add");
            screen.locked = false;
        });
    }
}
