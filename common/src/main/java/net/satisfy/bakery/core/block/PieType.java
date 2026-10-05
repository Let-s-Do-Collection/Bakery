package net.satisfy.bakery.core.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.foundation.util.ShapeUtil;


public enum PieType {
    CAKE(Block.box(1, 0, 1, 15, 8, 15), 4, 8, 0.0F),
    CHOCOLATE_GATEAU(Block.box(1, 0, 1, 15, 8, 15), 1, 8, 0.0F),
    BUNDT_CAKE(Block.box(4, 0, 4, 12, 8, 12), 1, 8, 0.0F),
    PUDDING(Shapes.or(Block.box(4, 0, 4, 12, 8, 12), Block.box(5, 8, 5, 11, 9, 11)), 1, 9, 0.0F),
    APPLE_PIE(Shapes.or(Block.box(2, 0, 2, 14, 4, 14), Block.box(1, 4, 1, 15, 6, 15)), 4, 6, 0.0F),
    GLOWBERRY_TART(Block.box(1, 0, 1, 15, 5, 15), 4, 5, 0.0F),
    LINZER_TART(Block.box(1, 0, 1, 15, 3, 15), 4, 3, 0.0F),
    CHOCOLATE_TART(Block.box(3, 0, 3, 13, 4, 13), 4, 4, 1.0F);

    private final VoxelShape[][] shapes = new VoxelShape[Cuts.LEFT.length][4];
    private final int maxCandles;
    private final int candleHeight;
    private final float candleInset;

    PieType(VoxelShape full, int maxCandles, int candleHeight, float candleInset) {
        this.maxCandles = maxCandles;
        this.candleHeight = candleHeight;
        this.candleInset = candleInset;
        for (int cuts = 0; cuts < Cuts.LEFT.length; cuts++) {
            VoxelShape left = Shapes.join(full, Cuts.LEFT[cuts], BooleanOp.AND);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                this.shapes[cuts][direction.get2DDataValue()] = ShapeUtil.rotateShape(Direction.NORTH, direction, left);
            }
        }
    }

    public VoxelShape shape(int cuts, Direction facing) {
        return this.shapes[cuts][facing.get2DDataValue()];
    }

    public int maxCandles() {
        return this.maxCandles;
    }

    public int candleHeight() {
        return this.candleHeight;
    }

    public float candleInset() {
        return this.candleInset;
    }

    private static final class Cuts {
        private static final VoxelShape[] LEFT = {
                Shapes.block(),
                Shapes.or(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 0, 8, 16, 16, 16)),
                Block.box(0, 0, 8, 16, 16, 16),
                Block.box(0, 0, 8, 8, 16, 16)
        };
    }
}
