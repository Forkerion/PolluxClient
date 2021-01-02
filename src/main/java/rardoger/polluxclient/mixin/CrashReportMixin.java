/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.Config;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.modules.Module;
import rardoger.polluxclient.modules.ModuleManager;
import rardoger.polluxclient.modules.ToggleModule;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Inject(method = "addStackTrace", at = @At("TAIL"))
    private void onAddStackTrace(StringBuilder sb, CallbackInfo info) {
        if (ModuleManager.INSTANCE != null) {
            sb.append("\n\n");
            sb.append("-- Pollux Client --\n");
            sb.append("Version: ").append(Config.INSTANCE.version.getOriginalString()).append("\n");

            if (!Config.INSTANCE.devBuild.isEmpty()) {
                sb.append("Dev Build: ").append(Config.INSTANCE.devBuild).append("\n");
            }

            for (Category category : ModuleManager.CATEGORIES) {
                List<Module> modules = ModuleManager.INSTANCE.getGroup(category);
                boolean active = false;
                for (Module module : modules) {
                    if (module instanceof ToggleModule && ((ToggleModule) module).isActive()) {
                        active = true;
                        break;
                    }
                }

                if (active) {
                    sb.append("\n");
                    sb.append("[").append(category).append("]:").append("\n");

                    for (Module module : modules) {
                        if (module instanceof ToggleModule && ((ToggleModule) module).isActive()) {
                            sb.append(module.title).append(" (").append(module.name).append(")\n");
                        }
                    }
                }
            }
        }
    }
}
