/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.events.pollux;

import rardoger.polluxclient.events.Cancellable;
import rardoger.polluxclient.utils.misc.input.KeyAction;

public class KeyEvent extends Cancellable {
    public int key;
    public KeyAction action;
}
