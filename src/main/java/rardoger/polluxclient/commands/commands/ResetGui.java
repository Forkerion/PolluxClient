/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.Config;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ResetGui extends Command {
    public ResetGui() {
        super("reset-gui", "Resets ClickGUI positions.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Config.INSTANCE.guiConfig.clearWindowConfigs();
            Chat.info("The ClickGUI positioning has been reset.");

            return SINGLE_SUCCESS;
        });
    }
}
