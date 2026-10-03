package net.satisfy.bakery.core.block;

import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import net.satisfy.foundation.block.CabinetBlock;
import net.satisfy.foundation.util.ShapeUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CabinetWallBlock extends CabinetBlock {
    private static final Supplier<VoxelShape> VOXEL_SHAPE_SUPPLIER = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.25, 1, 1, 1), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPES = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, VOXEL_SHAPE_SUPPLIER.get()));
        }
    });

    public CabinetWallBlock(BlockBehaviour.Properties settings, SoundEvent openSound, SoundEvent closeSound) {
        super(settings, EntityTypeRegistry.CABINET_BLOCK_ENTITY, openSound, closeSound);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return SHAPES.getOrDefault(direction, Shapes.empty());
    }
}
