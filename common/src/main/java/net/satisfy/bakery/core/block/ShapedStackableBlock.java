package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.foundation.block.StackableBlock;
import net.satisfy.foundation.util.ShapeUtil;
import org.jetbrains.annotations.NotNull;

public class ShapedStackableBlock extends StackableBlock {
    private final VoxelShape[][] shapes;

    public ShapedStackableBlock(Properties settings, int maxStack, VoxelShape... shapesNorthByStack) {
        super(settings, maxStack);
        this.shapes = new VoxelShape[shapesNorthByStack.length][4];
        for (int stack = 0; stack < shapesNorthByStack.length; stack++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                this.shapes[stack][direction.get2DDataValue()] = ShapeUtil.rotateShape(Direction.NORTH, direction, shapesNorthByStack[stack]).optimize();
            }
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int stack = Mth.clamp(state.getValue(STACK_PROPERTY) - 1, 0, shapes.length - 1);
        return shapes[stack][state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue()];
    }
}
