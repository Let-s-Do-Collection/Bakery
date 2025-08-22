package net.satisfy.bakery.core.block;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.StorageTypeRegistry;
import net.satisfy.farm_and_charm.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CupcakeDisplayBlock extends StorageBlock {
    public CupcakeDisplayBlock(Properties settings) {
        super(settings);
    }

    @Override
    public int size(){
        return 6;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.CUPCAKE_DISPLAY;
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return true;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.UP};
    }

    @Override
    public int getSection(Float x, Float y) {
        int i = y >= 0.5F ? 0 : 1;
        int j = getXSection(x);
        return j + i * 3;
    }

    private static int getXSection(float f) {
        if (f < 0.375F) {
            return 2;
        } else {
            return f < 0.6875F ? 1 : 0;
        }
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.9375, 0, 0.4375, 1, 0.875, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.4375, 0.0625, 0.875, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.0625, 0.3125, 0.9375, 0.375, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.3125, 0.9375, 0.75, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.8125, 0.4375, 0.9375, 0.875, 0.5625));

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    @SuppressWarnings({"deprecation"})
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.bakery.canbeplaced").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}