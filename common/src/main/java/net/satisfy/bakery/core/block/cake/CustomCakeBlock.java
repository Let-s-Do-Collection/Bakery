package net.satisfy.bakery.core.block.cake;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.block.entity.CustomCakeBlockEntity;
import net.satisfy.bakery.core.recipe.BlankCakeInteractionInput;
import net.satisfy.bakery.core.recipe.CakeDecorationRecipe;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class CustomCakeBlock extends BaseEntityBlock {
    public static final MapCodec<CustomCakeBlock> CODEC = simpleCodec(CustomCakeBlock::new);
    private static final VoxelShape SHAPE = Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375);

    public CustomCakeBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !world.isEmptyBlock(pos.below());
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide && !canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomCakeBlockEntity(pos, state);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (itemStack.isEmpty() || !(world.getBlockEntity(pos) instanceof CustomCakeBlockEntity cake)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (world.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        long gameTime = world.getGameTime();
        if (cake.isAnimating(gameTime)) {
            return ItemInteractionResult.CONSUME;
        }

        BlankCakeInteractionInput input = new BlankCakeInteractionInput(itemStack);
        List<RecipeHolder<CakeDecorationRecipe>> matches = world.getRecipeManager()
                .getRecipesFor(RecipeTypeRegistry.CAKE_DECORATION_TYPE.get(), input, world);

        if (matches.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        RecipeHolder<CakeDecorationRecipe> holder = matches.stream()
                .filter(match -> cake.accepts(match.value().layer()))
                .min(Comparator.comparing(match -> match.value().layer()))
                .orElse(null);

        if (holder == null) {
            cake.reject(gameTime);
            world.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.6F, 0.6F);
            player.displayClientMessage(Component.translatable("tooltip.bakery.custom_cake.layer_blocked." + matches.getFirst().value().layer().getSerializedName()), true);
            return ItemInteractionResult.CONSUME;
        }

        CakeDecorationRecipe recipe = holder.value();

        playSound(world, pos, recipe);

        if (cake.getPendingUses(holder.id()) + 1 < recipe.uses()) {
            cake.progress(holder.id(), recipe.animation(), recipe.part(), gameTime);
            return ItemInteractionResult.SUCCESS;
        }

        cake.apply(recipe, itemStack, gameTime);

        recipe.remainder()
                .flatMap(BuiltInRegistries.ITEM::getOptional)
                .map(ItemStack::new)
                .ifPresent(remainder -> {
                    if (!player.getInventory().add(remainder)) {
                        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, remainder));
                    }
                });

        if (recipe.consume() && !player.isCreative()) {
            itemStack.shrink(1);
        }

        return ItemInteractionResult.SUCCESS;
    }

    private static void playSound(Level world, BlockPos pos, CakeDecorationRecipe recipe) {
        recipe.sound()
                .flatMap(BuiltInRegistries.SOUND_EVENT::getOptional)
                .ifPresentOrElse(
                        sound -> world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F),
                        () -> world.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.2F)
                );
    }
}
