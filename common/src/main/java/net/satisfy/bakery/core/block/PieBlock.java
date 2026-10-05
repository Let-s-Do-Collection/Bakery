package net.satisfy.bakery.core.block;

import net.satisfy.foundation.util.ShapeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import java.util.ArrayList;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.SoundEventRegistry;
import net.satisfy.bakery.core.registry.TagsRegistry;
import net.satisfy.foundation.block.FacingBlock;
import net.satisfy.bakery.core.block.entity.CakeCandleBlockEntity;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class PieBlock extends FacingBlock implements EntityBlock {

    public static final IntegerProperty CUTS = IntegerProperty.create("cuts", 0, 3);
    public static final IntegerProperty CANDLE_COUNT = IntegerProperty.create("candle_count", 0, 4);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final float[][] CANDLE_SPOTS = {{4.0F, 11.0F, 0.0F}, {11.0F, 5.0F, 1.0F}, {12.0F, 12.0F, 2.0F}, {5.0F, 4.0F, 1.0F}};
    private static final List<Item> CANDLES = Util.make(new ArrayList<>(), list -> {
        list.add(Items.CANDLE);
        for (DyeColor color : DyeColor.values()) {
            list.add(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(color.getName() + "_candle")));
        }
    });
    public final Supplier<Item> Slice;
    private final PieType type;

    public PieBlock(Properties settings, PieType type, Supplier<Item> slice) {
        super(settings);
        this.type = type;
        this.Slice = slice != null ? slice : () -> Items.AIR;
        this.registerDefaultState(this.defaultBlockState().setValue(CUTS, 0).setValue(CANDLE_COUNT, 0).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CUTS, CANDLE_COUNT, LIT);
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
        int count = state.getValue(CANDLE_COUNT);
        int candle = count > 0 ? 1 : 0;
        if (state.getValue(CUTS) == 0 && heldStack.is(ItemTags.CANDLES) && CANDLES.contains(heldStack.getItem())) {
            if (count > 0 && (heldStack.getItem() != candleItem(level, pos) || count >= maxCandles())) {
                return ItemInteractionResult.CONSUME;
            }
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(CANDLE_COUNT, count + 1), 3);
                if (level.getBlockEntity(pos) instanceof CakeCandleBlockEntity entity) {
                    entity.setCandle(heldStack.getItem());
                }
                heldStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (candle > 0 && !state.getValue(LIT) && (heldStack.is(Items.FLINT_AND_STEEL) || heldStack.is(Items.FIRE_CHARGE))) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(LIT, true), 3);
                if (heldStack.is(Items.FLINT_AND_STEEL)) {
                    level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    heldStack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                } else {
                    level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    heldStack.consume(1, player);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (state.getValue(LIT) && heldStack.isEmpty()) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(LIT, false), 3);
                level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (candle > 0 && !level.isClientSide) {
            boolean cutting = heldStack.is(TagsRegistry.KNIVES) || heldStack.isEmpty() && player.isShiftKeyDown();
            if (cutting) {
                int remaining = maxCandles() == 1 ? 0 : Math.max(0, getMaxCuts() - state.getValue(CUTS) - 1);
                if (count > remaining) {
                    state = removeCandles(level, pos, state, count - remaining);
                }
            } else if (heldStack.isEmpty()) {
                state = removeCandles(level, pos, state, count);
            }
        }
        if (!level.isClientSide && !player.isShiftKeyDown() && state.getValue(CUTS) == 0 && heldStack.isEmpty() && candle == 0) {
            Direction direction = player.getDirection().getOpposite();
            Block.popResourceFromFace(level, pos, direction, new ItemStack(this));
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

    private static BlockState removeCandles(Level level, BlockPos pos, BlockState state, int amount) {
        Block.popResource(level, pos, new ItemStack(candleItem(level, pos), amount));
        int left = state.getValue(CANDLE_COUNT) - amount;
        BlockState changed = left <= 0 ? state.setValue(CANDLE_COUNT, 0).setValue(LIT, false) : state.setValue(CANDLE_COUNT, left);
        level.setBlock(pos, changed, 3);
        return changed;
    }

    private static Item candleItem(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof CakeCandleBlockEntity entity ? entity.getCandle() : Items.CANDLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CakeCandleBlockEntity(pos, state);
    }

    public int maxCandles() {
        return this.type.maxCandles();
    }

    public static int candleLight(BlockState state) {
        return state.getValue(LIT) ? 3 * state.getValue(CANDLE_COUNT) : 0;
    }

    public int candleHeight() {
        return this.type.candleHeight();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) {
            return;
        }
        for (int candle = 0; candle < state.getValue(CANDLE_COUNT); candle++) {
            float[] spot = candleSpot(state, candle);
            flame(level, random, pos.getX() + spot[0] / 16.0, pos.getY() + (spot[2] + 7.0) / 16.0, pos.getZ() + spot[1] / 16.0);
        }
    }

    protected float candleInset() {
        return this.type.candleInset();
    }

    public float[] candleSpot(BlockState state, int candle) {
        if (maxCandles() == 1) {
            return new float[]{8.0F, 8.0F, candleHeight()};
        }
        float x = CANDLE_SPOTS[candle][0], z = CANDLE_SPOTS[candle][1];
        x += x < 8.0F ? candleInset() : -candleInset();
        z += z < 8.0F ? candleInset() : -candleInset();
        int turns = (int) (state.getValue(FACING).toYRot() / 90.0F + 2) % 4;
        for (int turn = 0; turn < turns; turn++) {
            float rotated = 16.0F - z;
            z = x;
            x = rotated;
        }
        return new float[]{x, z, candleHeight() - CANDLE_SPOTS[candle][2]};
    }

    private static void flame(Level level, RandomSource random, double x, double y, double z) {
        level.addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0.0, 0.0, 0.0);
        if (random.nextInt(4) == 0) {
            level.addParticle(ParticleTypes.SMOKE, x, y + 0.05, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && state.getValue(CANDLE_COUNT) > 0) {
            Block.popResource(level, pos, new ItemStack(candleItem(level, pos), state.getValue(CANDLE_COUNT)));
        }
        super.onRemove(state, level, pos, newState, moved);
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
        Block.popResourceFromFace(level, pos, direction, this.getPieSliceItem());
        level.playSound(null, pos, SoundEventRegistry.CAKE_CUT.get(), SoundSource.PLAYERS, 0.75F, 0.75F);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return ShapeUtil.isFullAndSolid(levelReader, blockPos);
    }

    public int getMaxCuts() {
        return 4;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.type.shape(state.getValue(CUTS), state.getValue(FACING));
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

