/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.accounts;

import java.util.List;
import java.util.Map;

public class ProfileResponse {
    public List<Map<String, String>> properties;

    public String getTextures() {
        for (Map<String, String> map : properties) {
            if (map.get("name").equals("textures")) return map.get("value");
        }

        return null;
    }
}
