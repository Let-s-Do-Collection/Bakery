package net.satisfy.bakery.core.compat.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.satisfy.bakery.core.recipe.JamRecipe;

import java.util.Objects;

public class JamPotEmiRecipe extends BasicEmiRecipe {
    private static final int INPUTS = 3;
    private final int cookingTime;

    public JamPotEmiRecipe(RecipeHolder<JamRecipe> holder) {
        super(BakeryEMIPlugin.JAM_COOKING, holder.id(), 118, 26);
        JamRecipe recipe = holder.value();
        this.cookingTime = recipe.getCookingTime();
        recipe.getIngredients().stream().limit(INPUTS).map(EmiIngredient::of).forEach(inputs::add);
        outputs.add(EmiStack.of(recipe.getResultItem(Objects.requireNonNull(Minecraft.getInstance().level).registryAccess())));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        for (int i = 0; i < INPUTS; i++) {
            widgets.addSlot(i < inputs.size() ? inputs.get(i) : EmiStack.EMPTY, i * 18, 4);
        }
        widgets.addFillingArrow(60, 5, Math.max(cookingTime, 1) * 50);
        widgets.addSlot(outputs.get(0), 92, 0).large(true).recipeContext(this);
    }
}
