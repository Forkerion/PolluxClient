package rardoger.polluxclient.events.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;

public class FluidCollisionShapeEvent {
    public BlockState state;
    public VoxelShape shape;
}
