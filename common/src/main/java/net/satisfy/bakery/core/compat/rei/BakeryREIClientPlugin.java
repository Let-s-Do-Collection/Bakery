package net.satisfy.bakery.core.compat.rei;

import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import dev.architectury.event.EventResult;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.satisfy.bakery.core.compat.rei.caking.BakerStationCategory;
import net.satisfy.bakery.core.compat.rei.caking.BakerStationDisplay;
import net.satisfy.bakery.core.compat.rei.jam.JamPotCategory;
import net.satisfy.bakery.core.compat.rei.jam.JamPotDisplay;
import net.satisfy.bakery.core.recipe.BakingStationRecipe;
import net.satisfy.bakery.core.recipe.JamRecipe;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class BakeryREIClientPlugin {
    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new BakerStationCategory());
        registry.addWorkstations(BakerStationCategory.BAKER_STATION_DISPLAY, EntryStacks.of(ObjectRegistry.BAKER_STATION.get()));
        registry.add(new JamPotCategory());
        registry.addWorkstations(JamPotDisplay.JAM_POT_DISPLAY, EntryStacks.of(ObjectRegistry.SMALL_COOKING_POT_ITEM.get()));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(BakingStationRecipe.class, RecipeTypeRegistry.BAKING_STATION_RECIPE_TYPE.get(), holder -> new BakerStationDisplay(holder.value()));
        registry.registerRecipeFiller(JamRecipe.class, RecipeTypeRegistry.JAM_COOKING_TYPE.get(), holder -> new JamPotDisplay(holder.value()));
        registry.registerVisibilityPredicate((category, display) -> display.getOutputEntries().stream()
                .flatMap(List::stream)
                .anyMatch(entry -> entry.getValue() instanceof ItemStack stack && stack.is(ObjectRegistry.BAKED_SWEET_DOUGH.get()))
                ? EventResult.interruptFalse() : EventResult.pass());
    }

    public static void registerEntries(EntryRegistry registry) {
        registry.removeEntry(EntryStacks.of(ObjectRegistry.BAKED_SWEET_DOUGH.get()));
    }

    public static List<Ingredient> ingredients(Recipe<RecipeInput> recipe, ItemStack stack) {
        List<Ingredient> l = new ArrayList<>(recipe.getIngredients());
        l.add(0, Ingredient.of(stack.getItem()));
        return l;
    }
}
