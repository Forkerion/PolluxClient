package rardoger.polluxclient.events.render;

import rardoger.polluxclient.events.Cancellable;
import net.minecraft.block.entity.BlockEntity;

public class RenderBlockEntityEvent extends Cancellable {
    public BlockEntity blockEntity;
}
