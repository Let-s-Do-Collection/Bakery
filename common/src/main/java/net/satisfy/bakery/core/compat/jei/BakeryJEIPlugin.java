package net.satisfy.bakery.core.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.compat.jei.category.BakerStationCategory;
import net.satisfy.bakery.core.compat.jei.category.JamPotCategory;
import net.satisfy.bakery.core.recipe.BakingStationRecipe;
import net.satisfy.bakery.core.recipe.JamRecipe;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class BakeryJEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BakerStationCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JamPotCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<RecipeHolder<BakingStationRecipe>> bakingRecipesHolders = rm.getAllRecipesFor(RecipeTypeRegistry.BAKING_STATION_RECIPE_TYPE.get());
        List<BakingStationRecipe> bakingRecipes = new ArrayList<>();
        bakingRecipesHolders.forEach(bakingStationRecipeRecipeHolder -> bakingRecipes.add(bakingStationRecipeRecipeHolder.value()));
        registration.addRecipes(BakerStationCategory.CAKING, bakingRecipes);
        registration.addRecipes(JamPotCategory.JAM_COOKING, rm.getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get()).stream().map(RecipeHolder::value).toList());

    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return Bakery.identifier("jei_plugin");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        runtime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(ObjectRegistry.BAKED_SWEET_DOUGH.get().getDefaultInstance()));
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        runtime.getRecipeManager().hideRecipes(RecipeTypes.CAMPFIRE_COOKING, hiddenRecipes(rm, RecipeType.CAMPFIRE_COOKING));
        runtime.getRecipeManager().hideRecipes(RecipeTypes.SMOKING, hiddenRecipes(rm, RecipeType.SMOKING));
    }

    private static <T extends AbstractCookingRecipe> List<RecipeHolder<T>> hiddenRecipes(RecipeManager rm, RecipeType<T> type) {
        return rm.getAllRecipesFor(type).stream()
                .filter(holder -> holder.value().getResultItem(Minecraft.getInstance().level.registryAccess()).is(ObjectRegistry.BAKED_SWEET_DOUGH.get()))
                .toList();
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ObjectRegistry.BAKER_STATION.get().asItem().getDefaultInstance(), BakerStationCategory.CAKING);
        registration.addRecipeCatalyst(ObjectRegistry.SMALL_COOKING_POT_ITEM.get().getDefaultInstance(), JamPotCategory.JAM_COOKING);
    }
}
