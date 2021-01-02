/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.gui.screens;

import com.g00fy2.versioncompare.Version;
import rardoger.polluxclient.Config;
import rardoger.polluxclient.gui.widgets.WButton;
import rardoger.polluxclient.gui.widgets.WHorizontalSeparator;
import rardoger.polluxclient.gui.widgets.WLabel;
import rardoger.polluxclient.gui.widgets.WTable;
import net.minecraft.util.Util;

public class NewUpdateScreen extends WindowScreen {
    public NewUpdateScreen(Version latestVer) {
        super("New Update", true);

        add(new WLabel("A new version of Pollux has been released."));
        row();

        add(new WHorizontalSeparator());

        WTable versionsT = add(new WTable()).getWidget();
        versionsT.add(new WLabel("Your version:"));
        versionsT.add(new WLabel(Config.INSTANCE.version.getOriginalString()));
        versionsT.row();
        versionsT.add(new WLabel("Latest version"));
        versionsT.add(new WLabel(latestVer.getOriginalString()));
        row();

        add(new WHorizontalSeparator());

        WTable buttonsT = add(new WTable()).getWidget();
        buttonsT.add(new WButton("Download " + latestVer.getOriginalString())).fillX().expandX().getWidget().action = () -> Util.getOperatingSystem().open("https://polluxclient.com/");
        buttonsT.add(new WButton("OK")).fillX().expandX().getWidget().action = this::onClose;
    }
}
