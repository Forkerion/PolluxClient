package rardoger.polluxclient.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.commands.arguments.PlayerArgumentType;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.combat.Swarm;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SwarmFollow extends Command {

    public SwarmFollow() {
        super("s", "(highlight)follow <?player>(default) - Follow a player. Defaults to the Queen.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("follow").executes(context -> {
                    Swarm swarm = ModuleManager.INSTANCE.get(Swarm.class);
                    if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null && MinecraftClient.getInstance().player != null) {
                        swarm.server.sendMessage(context.getInput() + " " + MinecraftClient.getInstance().player.getDisplayName().getString());
                    }
                    return SINGLE_SUCCESS;
                }).then(argument("name", PlayerArgumentType.player()).executes(context -> {
                    PlayerEntity playerEntity = context.getArgument("name", PlayerEntity.class);
                    Swarm swarm = ModuleManager.INSTANCE.get(Swarm.class);
                    if (swarm.currentMode == Swarm.Mode.Queen && swarm.server != null) {
                        swarm.server.sendMessage(context.getInput());
                    } else {
                        if (playerEntity != null) {
                            BaritoneAPI.getProvider().getPrimaryBaritone().getFollowProcess().follow(entity -> entity.getDisplayName().asString().equalsIgnoreCase(playerEntity.getDisplayName().asString()));
                        }
                    }
                    return SINGLE_SUCCESS;
                })
                )
        );
    }
}
