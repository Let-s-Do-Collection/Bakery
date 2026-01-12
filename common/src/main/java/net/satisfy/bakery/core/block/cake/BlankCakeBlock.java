package net.satisfy.bakery.core.block.cake;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
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
import net.satisfy.bakery.core.recipe.BlankCakeInteractionInput;
import net.satisfy.bakery.core.recipe.BlankCakeInteractionRecipe;
import net.satisfy.bakery.core.recipe.BlankCakeStage;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

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
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlankCakeStage stage = BlankCakeStage.fromState(state);
        BlankCakeInteractionInput input = new BlankCakeInteractionInput(itemStack);

        BlankCakeInteractionRecipe recipe = world.getRecipeManager()
                .getAllRecipesFor(RecipeTypeRegistry.BLANK_CAKE_INTERACTION_TYPE.get()).stream()
                .map(RecipeHolder::value)
                .filter(currentRecipe -> currentRecipe.matchesStage(stage))
                .filter(currentRecipe -> currentRecipe.matches(input, world))
                .min(Comparator.comparingInt(BlankCakeInteractionRecipe::priority))
                .orElse(null);

        if (recipe == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlankCakeInteractionRecipe.Result result = recipe.result();

        BlockState newState;
        if (result.setBlock() != null) {
            Block output = BuiltInRegistries.BLOCK.getOptional(result.setBlock()).orElse(null);
            if (output == null) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            newState = output.defaultBlockState();
        } else if (result.setState() != null) {
            BlankCakeInteractionRecipe.StatePatch patch = result.setState();
            newState = state.setValue(CAKE, patch.cake()).setValue(CUPCAKE, patch.cupcake()).setValue(COOKIE, patch.cookie());
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (result.cooldownTicks() > 0) {
            player.getCooldowns().addCooldown(itemStack.getItem(), result.cooldownTicks());
        }

        world.setBlock(pos, newState, 3);

        if (result.particles()) {
            world.levelEvent(2001, pos, Block.getId(newState));
        }

        if (result.sound() != null) {
            BuiltInRegistries.SOUND_EVENT.getOptional(result.sound())
                    .ifPresent(soundEvent -> world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F));
        }

        if (result.giveItem() != null) {
            ItemStack giveStack = BuiltInRegistries.ITEM.getOptional(result.giveItem())
                    .map(ItemStack::new)
                    .orElse(ItemStack.EMPTY);

            if (!giveStack.isEmpty()) {
                if (!player.getInventory().add(giveStack)) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), giveStack));
                }
            }
        }

        if (result.consumeOne() && !player.isCreative()) {
            itemStack.shrink(1);
        }

        return ItemInteractionResult.sidedSuccess(false);
    }
}