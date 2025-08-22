package net.satisfy.bakery.core.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.util.BakeryIdentifier;
import org.jetbrains.annotations.NotNull;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.recipe.BakingStationRecipe;

public class BakerStationCategory implements IRecipeCategory<BakingStationRecipe> {
    public static final RecipeType<BakingStationRecipe> CAKING = RecipeType.create(Bakery.MOD_ID, "caking", BakingStationRecipe.class);
    public final static ResourceLocation TEXTURE =
            BakeryIdentifier.identifier("textures/gui/baking_station.png");

    private final IDrawable background;
    private final IDrawable icon;

    public BakerStationCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ObjectRegistry.BAKER_STATION.get()));
    }

    @Override
    public @NotNull RecipeType<BakingStationRecipe> getRecipeType() {
        return CAKING;
    }

    @Override
    public @NotNull Component getTitle() {
        return ObjectRegistry.BAKER_STATION.get().getName();
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BakingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 25).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 43).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 35).addItemStack(recipe.getResultItem(null));
    }
}