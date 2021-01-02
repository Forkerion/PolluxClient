/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens;

import rardoger.polluxclient.gui.WidgetScreen;
import rardoger.polluxclient.gui.widgets.Cell;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.gui.widgets.WWindow;

public abstract class WindowScreen extends WidgetScreen {
    private final WWindow window;

    public WindowScreen(String title, boolean expanded) {
        super(title);

        window = super.add(new WWindow(title, expanded)).centerXY().getWidget();
    }

    @Override
    public <T extends WWidget> Cell<T> add(T widget) {
        return window.add(widget);
    }

    public void row() {
        window.row();
    }

    @Override
    public void clear() {
        window.clear();
    }
}
