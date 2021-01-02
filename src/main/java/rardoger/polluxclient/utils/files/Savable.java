/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.utils.files;

import com.google.common.io.Files;
import rardoger.polluxclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;

public abstract class Savable<T> implements ISerializable<T> {
    private final File file;

    public Savable(File file) {
        this.file = file;
    }

    public void save(File file) {
        try {
            File tempFile = File.createTempFile("pollux-client", file.getName());
            NbtIo.write(toTag(), tempFile);

            file.getParentFile().mkdirs();
            Files.copy(tempFile, file);
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save() {
        save(getFile());
    }

    public boolean load(File file) {
        try {
            if (file.exists()) {
                fromTag(NbtIo.read(file));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean load() {
        return load(getFile());
    }

    public File getFile() {
        return file;
    }
}
