package net.satisfy.bakery.core.block.cake;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.SoundEventRegistry;
import net.satisfy.bakery.core.registry.TagsRegistry;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation"})
public class BlankCakeBlock extends Block {
    public static final BooleanProperty CAKE = BooleanProperty.create("cake");
    public static final BooleanProperty CUPCAKE = BooleanProperty.create("cupcake");
    public static final BooleanProperty COOKIE = BooleanProperty.create("cookie");

    public BlankCakeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(CAKE, true).setValue(CUPCAKE, false).setValue(COOKIE, false));
    }

    private static final VoxelShape CAKE_SHAPE = Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375);
    private static final VoxelShape BASE_SHAPE = Shapes.empty();
    private static final VoxelShape CUPCAKE_SHAPE = Shapes.or(
            BASE_SHAPE,
            Shapes.box(0.125, 0, 0.125, 0.4375, 0.375, 0.4375),
            Shapes.box(0.125, 0, 0.5625, 0.4375, 0.375, 0.875),
            Shapes.box(0.5625, 0, 0.125, 0.875, 0.375, 0.4375),
            Shapes.box(0.5625, 0, 0.5625, 0.875, 0.375, 0.875)
    );

    private static final VoxelShape COOKIE_SHAPE = Shapes.or(
            BASE_SHAPE,
            Shapes.box(0.125, 0, 0.125, 0.4375, 0.0625, 0.4375),
            Shapes.box(0.125, 0, 0.5625, 0.4375, 0.0625, 0.875),
            Shapes.box(0.5625, 0, 0.125, 0.875, 0.0625, 0.4375),
            Shapes.box(0.5625, 0, 0.5625, 0.875, 0.0625, 0.875)
    );

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(CAKE)) {
            return CAKE_SHAPE;
        } else if (state.getValue(CUPCAKE)) {
            return CUPCAKE_SHAPE;
        } else if (state.getValue(COOKIE)) {
            return COOKIE_SHAPE;
        } else {
            return Shapes.empty();
        }
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
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(CAKE, CUPCAKE, COOKIE);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            var item = itemStack.getItem();
            boolean isCake = state.getValue(CAKE);
            boolean isCupcake = state.getValue(CUPCAKE);
            boolean isCookie = state.getValue(COOKIE);

            if (item instanceof BlockItem) {
                var block = ((BlockItem) item).getBlock();
                boolean matched = false;

                if (isCake) {
                    if (block == ObjectRegistry.STRAWBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.STRAWBERRY_CAKE.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.STRAWBERRY_CAKE.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.CHOCOLATE_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.CHOCOLATE_CAKE.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.CHOCOLATE_CAKE.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.SWEETBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.SWEETBERRY_CAKE.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.SWEETBERRY_CAKE.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }                    }
                } else if (isCupcake) {
                    if (block == ObjectRegistry.STRAWBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.STRAWBERRY_CUPCAKE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.STRAWBERRY_CUPCAKE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.APPLE_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.APPLE_CUPCAKE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.APPLE_CUPCAKE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.SWEETBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.SWEETBERRY_CUPCAKE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.SWEETBERRY_CUPCAKE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    }
                } else if (isCookie) {
                    if (block == ObjectRegistry.STRAWBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.STRAWBERRY_COOKIE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.STRAWBERRY_COOKIE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.CHOCOLATE_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.CHOCOLATE_COOKIE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.CHOCOLATE_COOKIE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    } else if (block == ObjectRegistry.SWEETBERRY_JAM.get()) {
                        world.setBlock(pos, ObjectRegistry.SWEETBERRY_COOKIE_BLOCK.get().defaultBlockState(), 3);
                        world.levelEvent(2001, pos, Block.getId(ObjectRegistry.SWEETBERRY_COOKIE_BLOCK.get().defaultBlockState()));
                        matched = true;
                        if (!player.getInventory().add(ObjectRegistry.JAR.get().asItem().getDefaultInstance())) {
                            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ObjectRegistry.JAR.get().asItem().getDefaultInstance()));
                        }
                    }
                }
                if (matched) {
                    world.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return ItemInteractionResult.sidedSuccess(false);
                }
            } else {
                if (isCake && item == ObjectRegistry.CHOCOLATE_TRUFFLE.get()) {
                    player.getCooldowns().addCooldown(item, 20);
                    world.setBlock(pos, ObjectRegistry.CHOCOLATE_GATEAU.get().defaultBlockState(), 3);
                    world.levelEvent(2001, pos, Block.getId(ObjectRegistry.CHOCOLATE_GATEAU.get().defaultBlockState()));
                    world.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return ItemInteractionResult.sidedSuccess(false);
                } else if (isCake && itemStack.is(TagsRegistry.KNIVES)) {
                    world.setBlock(pos, state.setValue(CAKE, false).setValue(CUPCAKE, true), 3);
                    world.levelEvent(2001, pos, Block.getId(state));
                    world.playSound(null, pos, SoundEventRegistry.CAKE_CUT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.sidedSuccess(false);
                } else if (isCupcake && item == ObjectRegistry.ROLLING_PIN.get()) {
                    world.setBlock(pos, state.setValue(CUPCAKE, false).setValue(COOKIE, true), 3);
                    world.levelEvent(2001, pos, Block.getId(state));
                    world.playSound(null, pos, SoundEvents.GENERIC_BIG_FALL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.sidedSuccess(false);
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}