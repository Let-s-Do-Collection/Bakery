package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.StorageTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class TrayBlock extends CakeStandBlock {
    private static final VoxelShape SHAPE = Shapes.box(0.1875, 0.0, 0.125, 0.8125, 0.3125, 0.875);

    public TrayBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.TRAY;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public int getSection(Float f, Float y) {
        float oneSection = 1.0f / 9;
        int sectionIndex = (int) (f / oneSection);
        return 8 - sectionIndex;
    }
}