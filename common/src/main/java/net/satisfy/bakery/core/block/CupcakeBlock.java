package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation"})
public class CupcakeBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.125, 0, 0.125, 0.4375, 0.375, 0.4375),
            Shapes.box(0.125, 0, 0.5625, 0.4375, 0.375, 0.875),
            Shapes.box(0.5625, 0, 0.125, 0.875, 0.375, 0.4375),
            Shapes.box(0.5625, 0, 0.5625, 0.875, 0.375, 0.875)
    );

    public CupcakeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide) {
            if (!canSurvive(state, world, pos)) {
                world.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !world.isEmptyBlock(pos.below());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            if (state.is(ObjectRegistry.SWEETBERRY_CUPCAKE_BLOCK.get())) {
                popItem(world, pos, new ItemStack(ObjectRegistry.SWEETBERRY_CUPCAKE.get(), 4));
            } else if (state.is(ObjectRegistry.STRAWBERRY_CUPCAKE_BLOCK.get())) {
                popItem(world, pos, new ItemStack(ObjectRegistry.STRAWBERRY_CUPCAKE.get(), 4));
            } else if (state.is(ObjectRegistry.APPLE_CUPCAKE_BLOCK.get())) {
                popItem(world, pos, new ItemStack(ObjectRegistry.APPLE_CUPCAKE.get(), 4));
            }
            world.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.levelEvent(2001, pos, Block.getId(Blocks.CAKE.defaultBlockState()));
            world.removeBlock(pos, false);
            return ItemInteractionResult.sidedSuccess(false);
        }
        return super.useItemOn(itemStack, state, world, pos, player, hand, hit);
    }

    private void popItem(Level world, BlockPos pos, ItemStack stack) {
        Block.popResource(world, pos, stack);
    }
}
