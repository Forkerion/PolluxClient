/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.commands.arguments.ModuleArgumentType;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Bind extends Command {
    public Bind() {
        super("bind", "Binds a module to a specified key.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                .executes(context -> {
                    Module m = context.getArgument("module", Module.class);

                    Chat.info("Press a key you want this module to be bound to.");
                    ModuleManager.INSTANCE.setModuleToBind(m);

                    return SINGLE_SUCCESS;
                }));
    }
}
