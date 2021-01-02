/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.commands.arguments.ModuleArgumentType;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.settings.Setting;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Reset extends Command {
    public Reset() {
        super("reset", "Resets to the module's old settings.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                .executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    module.settings.forEach(group -> group.forEach(Setting::reset));
                    return SINGLE_SUCCESS;
                }));
    }
}
