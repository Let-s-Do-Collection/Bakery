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
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
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
    private static final VoxelShape QUARTER_NORTH = Block.box(0, 0, 8, 8, 16, 16);
    private static final double[][] CUT_ORDER_NORTH = {{12, 4}, {4, 4}, {12, 12}, {4, 12}};
    private static final AABB[] QUARTER_BOUNDS = new AABB[4];
    private static final int[][] PIECES_BY_CUTS = new int[4][4];
    public static final int ALL_PIECES = 0b1111;

    static {
        for (Direction quarter : Direction.Plane.HORIZONTAL) {
            QUARTER_BOUNDS[quarter.get2DDataValue()] = ShapeUtil.rotateShape(Direction.NORTH, quarter, QUARTER_NORTH).bounds();
        }
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (int cuts = 0; cuts < 4; cuts++) {
                int pieces = 0;
                for (int i = cuts; i < CUT_ORDER_NORTH.length; i++) {
                    double x = CUT_ORDER_NORTH[i][0];
                    double z = CUT_ORDER_NORTH[i][1];
                    Vec3 center = ShapeUtil.rotateShape(Direction.NORTH, facing, Block.box(x - 0.5, 0, z - 0.5, x + 0.5, 1, z + 0.5)).bounds().getCenter();
                    pieces |= 1 << quarterAt(center.x, center.z).get2DDataValue();
                }
                PIECES_BY_CUTS[facing.get2DDataValue()][cuts] = pieces;
            }
        }
    }

    public final Supplier<Item> Slice;
    private final PieType type;
    private final VoxelShape[][] shapesByPieces = new VoxelShape[4][16];

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

    public static int piecesFromCuts(BlockState state) {
        if (!state.hasProperty(CUTS) || !state.hasProperty(FACING)) {
            return ALL_PIECES;
        }
        return PIECES_BY_CUTS[state.getValue(FACING).get2DDataValue()][state.getValue(CUTS)];
    }

    public static Direction quarterAt(double x, double z) {
        for (Direction quarter : Direction.Plane.HORIZONTAL) {
            AABB bounds = QUARTER_BOUNDS[quarter.get2DDataValue()];
            if (x >= bounds.minX && x <= bounds.maxX && z >= bounds.minZ && z <= bounds.maxZ) {
                return quarter;
            }
        }
        return Direction.NORTH;
    }

    public static boolean hasPiece(int pieces, Direction quarter) {
        return (pieces & (1 << quarter.get2DDataValue())) != 0;
    }

    private static int pieces(BlockGetter level, BlockPos pos, BlockState state) {
        return level.getBlockEntity(pos) instanceof CakeCandleBlockEntity entity ? entity.getPieces() : piecesFromCuts(state);
    }

    private Direction pickQuarter(int pieces, BlockPos pos, BlockHitResult hit) {
        Vec3 local = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        Direction quarter = quarterAt(local.x, local.z);
        if (hasPiece(pieces, quarter)) {
            return quarter;
        }
        Direction closest = null;
        double best = Double.MAX_VALUE;
        for (Direction candidate : Direction.Plane.HORIZONTAL) {
            if (!hasPiece(pieces, candidate)) {
                continue;
            }
            Vec3 center = QUARTER_BOUNDS[candidate.get2DDataValue()].getCenter();
            double distance = (center.x - local.x) * (center.x - local.x) + (center.z - local.z) * (center.z - local.z);
            if (distance < best) {
                best = distance;
                closest = candidate;
            }
        }
        return closest;
    }

    @Nullable
    private Vec3 removeQuarter(Level level, BlockPos pos, BlockState state, BlockHitResult hit) {
        int pieces = pieces(level, pos, state);
        Direction quarter = pickQuarter(pieces, pos, hit);
        if (quarter == null) {
            return null;
        }
        AABB bounds = this.type.shape(3, quarter).bounds();
        Vec3 spot = new Vec3(pos.getX() + bounds.getCenter().x, pos.getY() + bounds.maxY, pos.getZ() + bounds.getCenter().z);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), spot.x, spot.y - 0.05, spot.z, 14, 0.12, 0.05, 0.12, 0.05);
        }
        int remaining = pieces & ~(1 << quarter.get2DDataValue());
        if (remaining == 0) {
            level.removeBlock(pos, false);
            return spot;
        }
        if (level.getBlockEntity(pos) instanceof CakeCandleBlockEntity entity) {
            entity.setPieces(remaining);
        }
        level.setBlock(pos, state.setValue(CUTS, Math.min(3, getMaxCuts() - Integer.bitCount(remaining))), 3);
        return spot;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
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
            if (cutting || heldStack.isEmpty()) {
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
            return this.consumeBite(level, pos, state, player, blockHitResult);
        }
        if (!player.isShiftKeyDown() && heldStack.is(TagsRegistry.KNIVES)) {
            return cutSlice(level, pos, state, player, blockHitResult);
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

    protected ItemInteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn, BlockHitResult hit) {
        if (!playerIn.canEat(false)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        } else {
            ItemStack sliceStack = this.getPieSliceItem();
            FoodProperties sliceFood = sliceStack.get(DataComponents.FOOD);
            if (sliceFood != null) {
                playerIn.getFoodData().eat(sliceFood);
                if (this.getPieSliceItem().has(DataComponents.FOOD)) {
                    sliceFood.effects().forEach(possibleEffect -> playerIn.addEffect(new MobEffectInstance(possibleEffect.effect())));
                }
            }

            removeQuarter(level, pos, state, hit);
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return ItemInteractionResult.SUCCESS;
        }
    }

    protected ItemInteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        Vec3 spot = removeQuarter(level, pos, state, hit);
        if (spot == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        double outX = (spot.x - pos.getX() - 0.5) * 0.4;
        double outZ = (spot.z - pos.getZ() - 0.5) * 0.4;
        ItemEntity slice = new ItemEntity(level, spot.x, spot.y, spot.z, this.getPieSliceItem(), outX, 0.2, outZ);
        slice.setDefaultPickUpDelay();
        level.addFreshEntity(slice);
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
        int pieces = pieces(world, pos, state);
        int facing = state.getValue(FACING).get2DDataValue();
        VoxelShape shape = this.shapesByPieces[facing][pieces];
        if (shape == null) {
            shape = Shapes.empty();
            for (Direction quarter : Direction.Plane.HORIZONTAL) {
                if (hasPiece(pieces, quarter)) {
                    shape = Shapes.or(shape, this.type.shape(3, quarter));
                }
            }
            shape = shape.isEmpty() ? this.type.shape(state.getValue(CUTS), state.getValue(FACING)) : shape;
            this.shapesByPieces[facing][pieces] = shape;
        }
        return shape;
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

