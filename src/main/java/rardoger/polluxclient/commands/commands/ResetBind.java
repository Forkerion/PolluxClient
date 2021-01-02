/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.commands.arguments.ModuleArgumentType;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ResetBind extends Command {
    public ResetBind() {
        super("reset-bind", "Resets a module keybind.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                .executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    module.setKey(-1);
                    Chat.info("This bind has been reset.");

                    return SINGLE_SUCCESS;
                }));
    }
}
