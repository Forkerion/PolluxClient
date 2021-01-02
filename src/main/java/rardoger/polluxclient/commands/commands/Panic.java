/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Panic extends Command {
    public Panic() {
        super("panic", "Disables all modules. DOES NOT remove keybinds.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            new ArrayList<>(ModuleManager.INSTANCE.getActive()).forEach(ToggleModule::toggle);

            return SINGLE_SUCCESS;
        });
    }
}
