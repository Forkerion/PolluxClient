/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.utils.network;

public class OnlinePlayers {
    private static long lastPingTime;

    public static void update() {
        long time = System.currentTimeMillis();

        if (time - lastPingTime > 5 * 60 * 1000) {
            PolluxExecutor.execute(() -> HttpUtils.get("http://polluxclient.com:8082/api/online/ping"));
            lastPingTime = time;
        }
    }

    public static void leave() {
        PolluxExecutor.execute(() -> HttpUtils.get("http://polluxclient.com:8082/api/online/leave"));
    }
}
