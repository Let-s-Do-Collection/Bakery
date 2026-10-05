package net.satisfy.bakery.core.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.recipe.JamRecipe;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JamPotCategory implements IRecipeCategory<JamRecipe> {
    public static final RecipeType<JamRecipe> JAM_COOKING = RecipeType.create(Bakery.MOD_ID, "jam_cooking", JamRecipe.class);
    public static final int INPUTS = 3;
    private static final int WIDTH = 116;
    private static final int HEIGHT = 26;
    private static final int ARROW_X = 58;
    private static final int OUTPUT_X = 89;

    private final IDrawable slot;
    private final IDrawable outputSlot;
    private final IDrawable icon;
    private final IGuiHelper helper;
    private final Map<Integer, IDrawableAnimated> arrows = new HashMap<>();

    public JamPotCategory(IGuiHelper helper) {
        this.helper = helper;
        this.slot = helper.getSlotDrawable();
        this.outputSlot = helper.getOutputSlot();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.SMALL_COOKING_POT_ITEM.get().getDefaultInstance());
    }

    @Override
    public @NotNull RecipeType<JamRecipe> getRecipeType() {
        return JAM_COOKING;
    }

    @Override
    public @NotNull Component getTitle() {
        return ObjectRegistry.SMALL_COOKING_POT_ITEM.get().getDescription();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JamRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (int i = 0; i < INPUTS && i < ingredients.size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 1 + i * 18, 5).addIngredients(ingredients.get(i));
        }
        if (Minecraft.getInstance().level != null) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X + 4, 5)
                    .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        }
    }

    @Override
    public void draw(JamRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        for (int i = 0; i < INPUTS; i++) {
            slot.draw(guiGraphics, i * 18, 4);
        }
        outputSlot.draw(guiGraphics, OUTPUT_X, 0);
        arrows.computeIfAbsent(recipe.getCookingTime(), helper::createAnimatedRecipeArrow).draw(guiGraphics, ARROW_X, 5);
    }
}
