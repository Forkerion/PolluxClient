/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient;

import com.g00fy2.versioncompare.Version;
import rardoger.polluxclient.gui.GuiConfig;
import rardoger.polluxclient.modules.Category;
import rardoger.polluxclient.rendering.Fonts;
import rardoger.polluxclient.utils.Utils;
import rardoger.polluxclient.utils.files.Savable;
import rardoger.polluxclient.utils.misc.NbtUtils;
import rardoger.polluxclient.utils.render.color.SettingColor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config extends Savable<Config> {
    public static Config INSTANCE;

    public final Version version = new Version("0.3.8");
    public String devBuild;
    private String prefix = ".";
    public GuiConfig guiConfig = new GuiConfig();

    public boolean chatCommandsInfo = true;
    public boolean deleteChatCommandsInfo = true;

    public Map<Category, SettingColor> categoryColors = new HashMap<>();

    public Config() {
        super(new File(PolluxClient.FOLDER, "config.nbt"));

        devBuild = FabricLoader.getInstance().getModContainer("pollux-client").get().getMetadata().getCustomValue("pollux-client:devbuild").getAsString();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        save();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setCategoryColor(Category category, SettingColor color) {
        categoryColors.put(category, color);
        save();
    }

    public SettingColor getCategoryColor(Category category) {
        return categoryColors.get(category);
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putString("version", version.getOriginalString());
        tag.putString("prefix", prefix);
        tag.put("categoryColors", NbtUtils.mapToTag(categoryColors));
        tag.put("guiConfig", guiConfig.toTag());
        tag.putBoolean("chatCommandsInfo", chatCommandsInfo);
        tag.putBoolean("deleteChatCommandsInfo", deleteChatCommandsInfo);

        return tag;
    }

    @Override
    public Config fromTag(CompoundTag tag) {
        prefix = tag.getString("prefix");
        categoryColors = NbtUtils.mapFromTag(tag.getCompound("categoryColors"), Category::valueOf, tag1 -> new SettingColor().fromTag((CompoundTag) tag1));
        guiConfig.fromTag(tag.getCompound("guiConfig"));
        chatCommandsInfo = !tag.contains("chatCommandsInfo") || tag.getBoolean("chatCommandsInfo");
        deleteChatCommandsInfo = !tag.contains("deleteChatCommandsInfo") || tag.getBoolean("deleteChatCommandsInfo");

        // In 0.2.9 the default font was changed, detect when people load up 0.2.9 for the first time
        Version lastVer = new Version(tag.getString("version"));
        Version v029 = new Version("0.2.9");

        if (lastVer.isLowerThan(v029) && version.isAtLeast(v029)) {
            Fonts.reset();
        }

        // If you run 0.3.7 for the first time add pollux pvp to server list
        Version v037 = new Version("0.3.7");

        if (lastVer.isLowerThan(v037) && version.isAtLeast(v037)) {
            Utils.addPolluxPvpToServerList();
        }

        return this;
    }
}
