package net.satisfy.bakery.core.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.block.entity.StorageBlockEntity;
import net.satisfy.bakery.core.registry.StorageTypeRegistry;
import net.satisfy.farm_and_charm.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class BreadBox extends StorageBlock {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public BreadBox(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }


    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.1875, 0.9375, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.5, 0.25, 0.9375, 0.5625, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.0625, 0.25, 0.125, 0.5, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.0625, 0.75, 0.875, 0.5, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.0625, 0.1875, 0.125, 0.375, 0.25), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.875, 0.0625, 0.25, 0.9375, 0.5, 0.8125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.875, 0.0625, 0.1875, 0.9375, 0.375, 0.25), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                boolean nowOpen = !state.getValue(OPEN);
                world.setBlock(pos, state.setValue(OPEN, nowOpen), 3);
                world.playSound(null, pos, nowOpen ? SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN : SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return ItemInteractionResult.sidedSuccess(world.isClientSide);
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StorageBlockEntity shelfBlockEntity) {
            Optional<Tuple<Float, Float>> optional = GeneralUtil.getRelativeHitCoordinatesForBlockFace(hit, state.getValue(FACING), this.unAllowedDirections());
            if (optional.isEmpty()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else {
                Tuple<Float, Float> ff = optional.get();
                int i = this.getSection(ff.getA(), ff.getB());
                if (i == Integer.MIN_VALUE) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                } else if (!shelfBlockEntity.getInventory().get(i).isEmpty()) {
                    this.remove(world, pos, player, shelfBlockEntity, i);
                    return ItemInteractionResult.sidedSuccess(world.isClientSide);
                } else {
                    ItemStack stack = player.getItemInHand(hand);
                    if (!stack.isEmpty() && this.canInsertStack(stack)) {
                        this.add(world, pos, player, shelfBlockEntity, stack, i);
                        return ItemInteractionResult.sidedSuccess(world.isClientSide);
                    } else {
                        return ItemInteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.BREADBOX;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.has(DataComponents.FOOD) || stack.getItem() instanceof BlockItem;
    }

    @Override
    public int getSection(Float aFloat, Float aFloat1) {
        return 0;
    }
}