package rardoger.polluxclient.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.packets.SendPacketEvent;
import rardoger.polluxclient.mixininterface.IPlayerMoveC2SPacket;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.modules.combat.Quiver;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoRotate extends ToggleModule {

    public NoRotate() {
        super(Category.Player, "no-rotate", "Attempts to block rotations sent from server to client");
    }

    @EventHandler
    private final Listener<SendPacketEvent> onSendPacket = new Listener<>(event -> {
        if (event.packet instanceof PlayerMoveC2SPacket) {
            if (ModuleManager.INSTANCE.get(EXPThrower.class).isActive() || ModuleManager.INSTANCE.get(Quiver.class).isActive()) return;
            ((IPlayerMoveC2SPacket) event.packet).setPitch(mc.player.getPitch(0));
            ((IPlayerMoveC2SPacket) event.packet).setYaw(mc.player.getYaw(1));
        }
    });
}
