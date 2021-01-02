package rardoger.polluxclient.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.PostTickEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.BoolSetting;
import rardoger.polluxclient.settings.ParticleEffectListSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import net.minecraft.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class Trail extends ToggleModule {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<ParticleEffect>> particles = sgGeneral.add(new ParticleEffectListSetting.Builder()
            .name("particles")
            .description("Particles to draw.")
            .defaultValue(new ArrayList<>(0))
            .build()
    );

    private final Setting<Boolean> pause = sgGeneral.add(new BoolSetting.Builder()
            .name("pause-when-stationary")
            .description("Whether or not to add particles when you are not moving.")
            .defaultValue(true)
            .build()
    );


    public Trail() {
        super(Category.Render, "trail", "Renders a trail behind your player.");
    }

    @EventHandler
    private final Listener<PostTickEvent> onTick = new Listener<>(event -> {
        if (pause.get() && mc.player.input.movementForward == 0f && mc.player.input.movementSideways == 0f && !mc.options.keyJump.isPressed()) return;
        for (ParticleEffect particleEffect : particles.get()) {
            mc.world.addParticle(particleEffect, mc.player.getX(), mc.player.getY(), mc.player.getZ(), 0, 0, 0);
        }
    });
}
