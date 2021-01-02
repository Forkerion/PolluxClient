/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts;

import rardoger.polluxclient.PolluxClient;
import rardoger.polluxclient.accounts.types.CrackedAccount;
import rardoger.polluxclient.accounts.types.PremiumAccount;
import rardoger.polluxclient.accounts.types.TheAlteningAccount;
import rardoger.polluxclient.events.EventStore;
import rardoger.polluxclient.utils.files.Savable;
import rardoger.polluxclient.utils.misc.NbtException;
import rardoger.polluxclient.utils.misc.NbtUtils;
import rardoger.polluxclient.utils.network.PolluxExecutor;
import net.minecraft.nbt.CompoundTag;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountManager extends Savable<AccountManager> implements Iterable<Account<?>> {
    public static final AccountManager INSTANCE = new AccountManager();

    private List<Account<?>> accounts = new ArrayList<>();

    private AccountManager() {
        super(new File(PolluxClient.FOLDER, "accounts.nbt"));
    }

    public void add(Account<?> account) {
        accounts.add(account);
        PolluxClient.EVENT_BUS.post(EventStore.accountListChangedEvent());
        save();
    }

    public void remove(Account<?> account) {
        if (accounts.remove(account)) {
            PolluxClient.EVENT_BUS.post(EventStore.accountListChangedEvent());
            save();
        }
    }

    public int size() {
        return accounts.size();
    }

    @Override
    public Iterator<Account<?>> iterator() {
        return accounts.iterator();
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.put("accounts", NbtUtils.listToTag(accounts));

        return tag;
    }

    @Override
    public AccountManager fromTag(CompoundTag tag) {
        PolluxExecutor.execute(() -> accounts = NbtUtils.listFromTag(tag.getList("accounts", 10), tag1 -> {
            CompoundTag t = (CompoundTag) tag1;
            if (!t.contains("type")) return null;

            AccountType type = AccountType.valueOf(t.getString("type"));

            try {
                Account<?> account = null;
                if (type == AccountType.Cracked) {
                    account = new CrackedAccount(null).fromTag(t);
                } else if (type == AccountType.Premium) {
                    account = new PremiumAccount(null, null).fromTag(t);
                } else if (type == AccountType.TheAltening) {
                    account = new TheAlteningAccount(null).fromTag(t);
                }

                if (account.fetchHead()) return account;
            } catch (NbtException e) {
                return null;
            }

            return null;
        }));

        return this;
    }
}
