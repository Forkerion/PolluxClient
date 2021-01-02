package rardoger.polluxclient.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import rardoger.polluxclient.commands.Command;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.combat.Swarm;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SwarmEscape extends Command {

    public SwarmEscape() {
        super("s", "(highlight)escape(default)- Removes this player from the active swarm.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("escape").executes(context -> {
                    Swarm swarm = ModuleManager.INSTANCE.get(Swarm.class);
                    if (swarm.isActive()) {
                        if (swarm.currentMode != Swarm.Mode.Queen) {
                            swarm.closeAllServerConnections();
                            if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing())
                                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
                            swarm.currentMode = Swarm.Mode.Idle;
                            ModuleManager.INSTANCE.get(Swarm.class).toggle();
                        } else {
                            Chat.info("Swarm: You are the queen.");
                        }
                    }
                    return SINGLE_SUCCESS;
                })
        );
    }
}
