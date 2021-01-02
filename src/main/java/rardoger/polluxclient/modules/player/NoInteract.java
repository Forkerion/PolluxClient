package rardoger.polluxclient.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.game.OpenScreenEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class NoInteract extends ToggleModule {

    public NoInteract() {
        super(Category.Player, "no-interact", "Blocks interactions with certain types of inputs.");
    }

    @EventHandler
    private final Listener<OpenScreenEvent> onScreenOpen = new Listener<>(event -> {
        if (event.screen == null) return;
        if (!event.screen.isPauseScreen() && !(event.screen instanceof AbstractInventoryScreen) && (event.screen instanceof HandledScreen)) event.setCancelled(true);
    });
}
