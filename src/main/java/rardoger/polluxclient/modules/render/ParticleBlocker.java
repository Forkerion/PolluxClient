package rardoger.polluxclient.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import rardoger.polluxclient.events.world.ParticleEvent;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.ToggleModule;
import rardoger.polluxclient.settings.ParticleEffectListSetting;
import rardoger.polluxclient.settings.Setting;
import rardoger.polluxclient.settings.SettingGroup;
import net.minecraft.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class ParticleBlocker extends ToggleModule {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<ParticleEffect>> particles = sgGeneral.add(new ParticleEffectListSetting.Builder()
            .name("particles")
            .description("Particles to block.")
            .defaultValue(new ArrayList<>(0))
            .build()
    );

    public ParticleBlocker() {
        super(Category.Render, "particle-blocker", "Stops specified particles from rendering.");
    }

    @EventHandler
    private final Listener<ParticleEvent> onRenderParticle = new Listener<>(event -> {
        if (event.particle != null && particles.get().contains(event.particle)) event.cancel();
    });
}
