package net.satisfy.bakery.core.recipe;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.BlankCakeBlock;
import org.jetbrains.annotations.NotNull;

public enum BlankCakeStage implements StringRepresentable {
    CAKE("cake"),
    DOUGH("dough"),
    CUPCAKE("cupcake"),
    FLAT("flat"),
    COOKIE("cookie"),
    SHEET("sheet"),
    ROLL("roll"),
    SHELLS("shells");

    private final String name;

    BlankCakeStage(String name) {
        this.name = name;
    }

    public static BlankCakeStage fromState(BlockState state) {
        return state.getValue(BlankCakeBlock.STAGE);
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
