package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TableBlock extends Block {
    public TableBlock(Properties properties) {
        super(properties);
    }

    private final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.4375, 0.1875, 0.4375, 0.5625, 0.8125, 0.5625),
            Shapes.box(0, 0.8125, 0, 1, 1, 1)
    );

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
