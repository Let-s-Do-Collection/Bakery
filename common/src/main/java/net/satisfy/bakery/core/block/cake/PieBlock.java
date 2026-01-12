package net.satisfy.bakery.core.block.cake;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.SoundEventRegistry;
import net.satisfy.bakery.core.registry.TagsRegistry;
import net.satisfy.farm_and_charm.core.block.FacingBlock;
import net.satisfy.farm_and_charm.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PieBlock extends FacingBlock {

    public static final IntegerProperty CUTS = IntegerProperty.create("cuts", 0, 3);
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.25, 1, 1, 1), BooleanOp.OR);
        return shape;
    };
    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });
    public final Supplier<Item> Slice;

    public PieBlock(Properties settings, Supplier<Item> slice) {
        super(settings);
        this.Slice = slice != null ? slice : () -> Items.AIR;
        this.registerDefaultState(this.defaultBlockState().setValue(CUTS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CUTS);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return getMaxCuts() - blockState.getValue(CUTS);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public ItemStack getPieSliceItem() {
        return new ItemStack(this.Slice != null ? this.Slice.get() : Items.AIR);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!level.isClientSide && !player.isShiftKeyDown() && state.getValue(CUTS) == 0 && heldStack.isEmpty()) {
            Direction direction = player.getDirection().getOpposite();
            double xMotion = direction.getStepX() * 0.13;
            double yMotion = 0.35;
            double zMotion = direction.getStepZ() * 0.13;
            GeneralUtil.spawnSlice(level, new ItemStack(this), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
            level.removeBlock(pos, false);
            return ItemInteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown() && (heldStack.isEmpty() || heldStack.is(TagsRegistry.KNIVES))) {
            return this.consumeBite(level, pos, state, player);
        }
        if (!player.isShiftKeyDown() && heldStack.is(TagsRegistry.KNIVES)) {
            return cutSlice(level, pos, state, player);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected ItemInteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
        if (!playerIn.canEat(false)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            ItemStack sliceStack = this.getPieSliceItem();
            FoodProperties sliceFood = sliceStack.get(DataComponents.FOOD);
            if (sliceFood != null) {
                playerIn.getFoodData().eat(sliceFood);
                if (this.getPieSliceItem().has(DataComponents.FOOD)) {
                    sliceFood.effects().forEach(possibleEffect -> playerIn.addEffect(new MobEffectInstance(possibleEffect.effect())));
                }
            }

            int cuts = state.getValue(CUTS);
            if (cuts < getMaxCuts() - 1) {
                level.setBlock(pos, state.setValue(CUTS, cuts + 1), 3);
            } else {
                level.destroyBlock(pos, false);
            }
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return ItemInteractionResult.SUCCESS;
        }
    }

    protected ItemInteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int cuts = state.getValue(CUTS);
        if (cuts < getMaxCuts() - 1) {
            level.setBlock(pos, state.setValue(CUTS, cuts + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        double xMotion = direction.getStepX() * 0.13;
        double yMotion = 0.35;
        double zMotion = direction.getStepZ() * 0.13;

        GeneralUtil.spawnSlice(level, this.getPieSliceItem(), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, xMotion, yMotion, zMotion);
        level.playSound(null, pos, SoundEventRegistry.CAKE_CUT.get(), SoundSource.PLAYERS, 0.75F, 0.75F);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return GeneralUtil.isFullAndSolid(levelReader, blockPos);
    }

    public int getMaxCuts() {
        return 4;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        int icingRed = 0xE3A6A0;
        int gold = 0xFFD700;

        tooltip.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());

        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            tooltip.add(Component.translatable("tooltip.farm_and_charm.tooltip_information.hold", key)
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
            return;
        }

        tooltip.add(Component.translatable("tooltip.bakery.cake_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
        tooltip.add(Component.translatable("tooltip.bakery.cake_2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
        tooltip.add(Component.translatable("tooltip.bakery.cake_3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
    }
}

