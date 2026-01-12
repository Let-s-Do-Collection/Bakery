package net.satisfy.bakery.core.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record BlankCakeInteractionInput(ItemStack stack) implements RecipeInput {
        @Override
        public @NotNull ItemStack getItem(int index) {
            return index == 0 ? stack : ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return 1;
        }
    }