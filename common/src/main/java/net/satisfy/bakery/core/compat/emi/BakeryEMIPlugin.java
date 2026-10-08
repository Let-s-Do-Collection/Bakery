package net.satisfy.bakery.core.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeManager;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;

@EmiEntrypoint
public class BakeryEMIPlugin implements EmiPlugin {
    public static final EmiStack BAKER_STATION = EmiStack.of(ObjectRegistry.BAKER_STATION.get());
    public static final EmiStack COOKING_POT = EmiStack.of(ObjectRegistry.SMALL_COOKING_POT_ITEM.get());
    public static final EmiRecipeCategory BAKING = new EmiRecipeCategory(Bakery.identifier("baking_station"), BAKER_STATION) {
        @Override
        public Component getName() {
            return ObjectRegistry.BAKER_STATION.get().getName();
        }
    };
    public static final EmiRecipeCategory JAM_COOKING = new EmiRecipeCategory(Bakery.identifier("jam_cooking"), COOKING_POT) {
        @Override
        public Component getName() {
            return ObjectRegistry.SMALL_COOKING_POT_ITEM.get().getDescription();
        }
    };

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BAKING);
        registry.addCategory(JAM_COOKING);
        registry.addWorkstation(BAKING, BAKER_STATION);
        registry.addWorkstation(JAM_COOKING, COOKING_POT);

        RecipeManager rm = registry.getRecipeManager();
        rm.getAllRecipesFor(RecipeTypeRegistry.BAKING_STATION_RECIPE_TYPE.get()).forEach(holder -> registry.addRecipe(new BakingStationEmiRecipe(holder)));
        rm.getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get()).forEach(holder -> registry.addRecipe(new JamPotEmiRecipe(holder)));

        EmiStack hidden = EmiStack.of(ObjectRegistry.BAKED_SWEET_DOUGH.get());
        registry.removeEmiStacks(hidden);
        registry.removeRecipes(recipe -> recipe.getOutputs().stream().anyMatch(stack -> stack.isEqual(hidden)));
    }
}
