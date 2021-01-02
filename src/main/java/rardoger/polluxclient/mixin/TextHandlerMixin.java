/*
 * This file is part of the Pollux Client distribution (https://github.com/PolluxDevelopment/pollux-client/).
 * Copyright (c) 2020 Pollux Development.
 */

package rardoger.polluxclient.mixin;

import rardoger.polluxclient.mixininterface.ITextHandler;
import net.minecraft.client.font.TextHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextHandler.class)
public class TextHandlerMixin implements ITextHandler {
    @Shadow @Final private TextHandler.WidthRetriever widthRetriever;

    @Override
    public TextHandler.WidthRetriever getWidthRetriever() {
        return widthRetriever;
    }
}
