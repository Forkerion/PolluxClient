package rardoger.polluxclient.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.entity.EntityAddedEvent;
import rardoger.polluxclient.events.entity.EntityRemovedEvent;
import rardoger.polluxclient.friends.FriendManager;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import rardoger.polluxclient.settings.StringSetting;
import rardoger.polluxclient.utils.entity.FakePlayerEntity;
import rardoger.polluxclient.utils.player.Chat;
import net.minecraft.entity.player.PlayerEntity;

public class VisualRange extends ToggleModule {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> ignoreFriends = sgGeneral.add(new BoolSetting.Builder()
            .name("ignore-friends")
            .description("Ignores friends.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> ignoreFakes = sgGeneral.add(new BoolSetting.Builder()
            .name("ignore-fakeplayers")
            .description("Ignores fake players.")
            .defaultValue(true)
            .build()
    );

    private final Setting<String> enterMessage = sgGeneral.add(new StringSetting.Builder()
            .name("enter-message")
            .description("The message for when a player enters your visual range.")
            .defaultValue("{player} has entered your visual range.")
            .build()
    );

    private final Setting<String> leaveMessage = sgGeneral.add(new StringSetting.Builder()
            .name("leave-message")
            .description("The message for when a player leaves your visual range.")
            .defaultValue("{player} has left your visual range.")
            .build()
    );


    public VisualRange() {
        super(Category.Misc, "visual-range", "Notifies you when a player enters/leaves your visual range.");
    }

    @EventHandler
    private final Listener<EntityAddedEvent> onEntityAdded = new Listener<>(event -> {
        if (event.entity.equals(mc.player) || !(event.entity instanceof PlayerEntity) || !FriendManager.INSTANCE.attack((PlayerEntity) event.entity) && ignoreFriends.get() || (event.entity instanceof FakePlayerEntity && ignoreFakes.get())) return;

        String enter = enterMessage.get().replace("{player}", ((PlayerEntity) event.entity).getGameProfile().getName());
        Chat.info(this, enter);
    });

    @EventHandler
    private final Listener<EntityRemovedEvent> onEntityRemoved = new Listener<>(event -> {
        if (event.entity.equals(mc.player) || !(event.entity instanceof PlayerEntity) || !FriendManager.INSTANCE.attack((PlayerEntity) event.entity) && ignoreFriends.get() || (event.entity instanceof FakePlayerEntity && ignoreFakes.get())) return;

        String leave = leaveMessage.get().replace("{player}", ((PlayerEntity) event.entity).getGameProfile().getName());
        Chat.info(this, leave);
    });
}
