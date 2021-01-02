package rardoger.polluxclient.commands.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.commands.arguments.ModuleArgumentType;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.modules.combat.Swarm;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SwarmModuleToggle extends Command {

    public SwarmModuleToggle() {
        super("s", "(highlight)module <module> <true/false>(default) - Toggle a module on or off.");
    }


    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("module").then(argument("m", ModuleArgumentType.module()).then(argument("bool", BoolArgumentType.bool()).executes(context -> {
            Swarm swarm = ModuleManager.INSTANCE.get(Swarm.class);
            if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                swarm.server.sendMessage(context.getInput());
            } else {
                ToggleModule module = (ToggleModule) context.getArgument("m", Module.class);
                if (module.isActive() != context.getArgument("bool", Boolean.class)) {
                    module.toggle();
                }
            }
            return SINGLE_SUCCESS;
        }))));
    }
}
