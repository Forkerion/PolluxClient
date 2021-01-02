/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens.topbar;

import rardoger.polluxclient.gui.widgets.Cell;
import rardoger.polluxclient.gui.widgets.WWidget;
import rardoger.polluxclient.gui.widgets.WWindow;

public abstract class TopBarWindowScreen extends TopBarScreen {
    private final WWindow window;

    public TopBarWindowScreen(TopBarType type) {
        super(type);

        window = super.add(new WWindow(type.toString(), true)).centerXY().getWidget();

        addTopBar();
        initWidgets();
    }

    protected abstract void initWidgets();

    @Override
    public <T extends WWidget> Cell<T> add(T widget) {
        return window.add(widget);
    }

    protected void row() {
        window.row();
    }

    @Override
    public void clear() {
        window.clear();
    }
}
