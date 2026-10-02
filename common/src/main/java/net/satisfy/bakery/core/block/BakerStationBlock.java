package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.block.cake.BlankCakeBlock;
import net.satisfy.bakery.core.block.entity.BakerStationBlockEntity;
import net.satisfy.bakery.core.block.entity.BlankCakeBlockEntity;
import net.satisfy.bakery.core.recipe.BlankCakeStage;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.foundation.block.FacingBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BakerStationBlock extends FacingBlock implements EntityBlock {
    public static final TagKey<Item> FLOUR = TagKey.create(Registries.ITEM, Bakery.identifier("flour"));
    public static final TagKey<Item> TOOLS = TagKey.create(Registries.ITEM, Bakery.identifier("baker_station_tools"));

    public BakerStationBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.isEmptyBlock(pos.above());
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (itemStack.is(TOOLS) && world.isEmptyBlock(pos.above()) && world.getBlockEntity(pos) instanceof BakerStationBlockEntity station) {
                if (station.addTool(slotAt(blockState, pos, blockHitResult), player.isCreative() ? itemStack.copy() : itemStack)) {
                    world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.8F, 1.1F);
                    return ItemInteractionResult.SUCCESS;
                }
                return ItemInteractionResult.CONSUME;
            }
            if (itemStack.is(FLOUR)) {
                dustWithFlour((ServerLevel) world, pos.above(), itemStack);
                return ItemInteractionResult.SUCCESS;
            }
            boolean hasTools = world.getBlockEntity(pos) instanceof BakerStationBlockEntity station && station.getTools().stream().anyMatch(tool -> !tool.isEmpty());
            boolean placeable = itemStack.is(ObjectRegistry.CAKE_DOUGH.get()) || itemStack.is(ObjectRegistry.SWEET_DOUGH.get())
                    || itemStack.is(ObjectRegistry.SPONGE_SHEET.get()) || itemStack.is(ObjectRegistry.CORNET_SHELL.get());
            if (hasTools && placeable) {
                return ItemInteractionResult.CONSUME;
            }
            if ((itemStack.is(ObjectRegistry.SPONGE_SHEET.get()) || itemStack.is(ObjectRegistry.CORNET_SHELL.get())) && world.isEmptyBlock(pos.above())) {
                boolean sheet = itemStack.is(ObjectRegistry.SPONGE_SHEET.get());
                BlockState placed = ObjectRegistry.BLANK_CAKE.get().defaultBlockState().setValue(BlankCakeBlock.STAGE, sheet ? BlankCakeStage.SHEET : BlankCakeStage.SHELLS);
                world.setBlock(pos.above(), placed, 3);
                if (!sheet && world.getBlockEntity(pos.above()) instanceof BlankCakeBlockEntity cake) {
                    cake.addShell(itemStack);
                }
                world.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return ItemInteractionResult.SUCCESS;
            }
            if (itemStack.is(ObjectRegistry.CAKE_DOUGH.get())) {
                BlockPos blockAbove = pos.above();
                if (world.isEmptyBlock(blockAbove)) {
                    world.setBlock(blockAbove, ObjectRegistry.BLANK_CAKE.get().defaultBlockState(), 3);
                    world.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.levelEvent(2001, blockAbove, Block.getId(ObjectRegistry.BLANK_CAKE.get().defaultBlockState()));
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (itemStack.is(ObjectRegistry.SWEET_DOUGH.get())) {
                BlockPos blockAbove = pos.above();
                if (world.isEmptyBlock(blockAbove)) {
                    BlockState dough = ObjectRegistry.BLANK_CAKE.get().defaultBlockState().setValue(BlankCakeBlock.STAGE, BlankCakeStage.DOUGH);
                    world.setBlock(blockAbove, dough, 3);
                    world.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.levelEvent(2001, blockAbove, Block.getId(dough));
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        int slot = slotAt(state, pos, hit);
        if (!player.getMainHandItem().isEmpty() || !(world.getBlockEntity(pos) instanceof BakerStationBlockEntity station) || station.getTool(slot).isEmpty()) {
            return InteractionResult.PASS;
        }
        if (!world.isClientSide) {
            ItemStack tool = station.takeTool(slot);
            if (!player.getInventory().add(tool)) {
                player.drop(tool, false);
            }
            world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.8F, 1.1F);
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    public static int slotAt(BlockState state, BlockPos pos, BlockHitResult hit) {
        Direction facing = state.getValue(FACING);
        double angle = Math.toRadians(180.0 - facing.toYRot());
        double dx = hit.getLocation().x - (pos.getX() + 0.5);
        double dz = hit.getLocation().z - (pos.getZ() + 0.5);
        double localX = dx * Math.cos(angle) - dz * Math.sin(angle);
        return localX < 0.0 ? 0 : 1;
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && world.getBlockEntity(pos) instanceof BakerStationBlockEntity station) {
            Containers.dropContents(world, pos, station.getTools());
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BakerStationBlockEntity(pos, state);
    }

    public static void dustWithFlour(ServerLevel world, BlockPos pos, ItemStack flour) {
        double x = pos.getX() + 0.5, y = pos.getY() + 0.1, z = pos.getZ() + 0.5;
        world.sendParticles(ParticleTypes.WHITE_SMOKE, x, y, z, 14, 0.3, 0.1, 0.3, 0.01);
        world.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, flour.copyWithCount(1)),
                x, y + 0.1, z, 8, 0.25, 0.05, 0.25, 0.04);
        world.playSound(null, pos, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 0.5F, 1.6F);
    }
}
