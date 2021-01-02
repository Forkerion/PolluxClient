/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.topbar;

import rardoger.polluxclient.gui.WidgetScreen;
import rardoger.polluxclient.gui.widgets.WTopBar;

public abstract class TopBarScreen extends WidgetScreen {
    public final TopBarType type;

    public TopBarScreen(TopBarType type) {
        super(type.toString());
        this.type = type;
    }

    protected void addTopBar() {
        super.add(new WTopBar()).centerX();
    }
}
