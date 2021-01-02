/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts;

public class ProfileSkinResponse {
    public Textures textures;

    public static class Textures {
        public Texture SKIN;
        public Texture CAPE;
    }

    public static class Texture {
        public String url;
    }
}
