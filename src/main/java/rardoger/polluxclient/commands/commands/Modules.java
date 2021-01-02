/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.Config;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import java.util.List;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Modules extends Command {
    public Modules() {
        super("modules", "Lists all modules.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Chat.info("All (highlight)%d (default)modules:", ModuleManager.INSTANCE.getAll().size());

            for (Category category : ModuleManager.CATEGORIES) {
                List<Module> group = ModuleManager.INSTANCE.getGroup(category);
                Chat.info("- (highlight)%s (default)(%d):", category.toString(), group.size());

                for (Module module : group) {
                    Chat.info("  - (highlight)%s%s (default)- %s", Config.INSTANCE.getPrefix(), module.name, module.description);
                }
            }

            return SINGLE_SUCCESS;
        });
    }
}
