/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.friends;

import rardoger.polluxclient.gui.screens.WindowScreen;

public class EditFriendScreen extends WindowScreen {
    public EditFriendScreen(Friend friend) {
        super(friend.name, true);
    }
}
