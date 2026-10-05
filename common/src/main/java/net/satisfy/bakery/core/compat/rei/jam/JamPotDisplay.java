package net.satisfy.bakery.core.compat.rei.jam;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.recipe.JamRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JamPotDisplay extends BasicDisplay {
    public static final CategoryIdentifier<JamPotDisplay> JAM_POT_DISPLAY = CategoryIdentifier.of(Bakery.MOD_ID, "jam_pot_display");
    public static final int INPUTS = 3;

    private final int cookingTime;

    public JamPotDisplay(JamRecipe recipe) {
        super(createInputs(recipe), Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))), Optional.empty());
        this.cookingTime = recipe.getCookingTime();
    }

    private static List<EntryIngredient> createInputs(JamRecipe recipe) {
        List<EntryIngredient> inputs = new ArrayList<>();
        for (int i = 0; i < INPUTS; i++) {
            inputs.add(i < recipe.getIngredients().size() ? EntryIngredients.ofIngredient(recipe.getIngredients().get(i)) : EntryIngredients.of(ItemStack.EMPTY));
        }
        return inputs;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return JAM_POT_DISPLAY;
    }
}
