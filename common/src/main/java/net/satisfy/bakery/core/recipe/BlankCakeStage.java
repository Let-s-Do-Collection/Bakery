package net.satisfy.bakery.core.recipe;

import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.cake.BlankCakeBlock;

public enum BlankCakeStage {
        CAKE,
        CUPCAKE,
        COOKIE;

        public static BlankCakeStage fromState(BlockState state) {
            if (state.getValue(BlankCakeBlock.CAKE)) {
                return CAKE;
            }
            if (state.getValue(BlankCakeBlock.CUPCAKE)) {
                return CUPCAKE;
            }
            return COOKIE;
        }
    }