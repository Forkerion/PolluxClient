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
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Settings extends Command {
    public Settings() {
        super("settings", "Displays all settings of specified module.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module()).executes(context -> {
            Module module = context.getArgument("module", Module.class);

            Chat.info("(highlight)%s(default):", module.title);
            for (SettingGroup sg : module.settings) {
                for (Setting<?> setting : sg) {
                    Chat.info("  Usage of (highlight)%s (default)(%s) is (highlight)%s(default).", setting.name, setting.get().toString(), setting.getUsage());
                }
            }

            return SINGLE_SUCCESS;
        }));
    }
}
