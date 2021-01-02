/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui;

public class GuiKeyEvents {
    public static int postKeyEvents = 0;

    public static void setPostKeyEvents(boolean post) {
        postKeyEvents += post ? 1 : -1;
    }
    public static boolean postKeyEvents() {
        return postKeyEvents <= 0;
    }
    public static void resetPostKeyEvents() {
        postKeyEvents = 0;
    }
}
