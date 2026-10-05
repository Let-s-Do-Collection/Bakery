package net.satisfy.bakery.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.bakery.core.block.entity.SmallCookingPotBlockEntity;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JamRecipe implements Recipe<JamRecipe.Input> {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int color;
    private final int cookingTime;
    private final float experience;

    public JamRecipe(List<Ingredient> ingredients, ItemStack result, int color, int cookingTime, float experience) {
        this.experience = experience;
        this.ingredients = NonNullList.of(Ingredient.EMPTY, ingredients.toArray(Ingredient[]::new));
        this.result = result;
        this.color = color;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(Input input, Level level) {
        return input.size() == ingredients.size() && accepts(input.items());
    }

    public boolean accepts(List<ItemStack> items) {
        return items.size() <= ingredients.size() && assign(items, 0, new boolean[ingredients.size()]);
    }

    private boolean assign(List<ItemStack> items, int index, boolean[] used) {
        if (index == items.size()) {
            return true;
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (!used[i] && ingredients.get(i).test(items.get(index))) {
                used[i] = true;
                if (assign(items, index + 1, used)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getColor() {
        return color;
    }

    public float getExperience() {
        return experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack assemble(Input input, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.JAM_COOKING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.JAM_COOKING_TYPE.get();
    }

    public record Input(List<ItemStack> items) implements RecipeInput {
        @Override
        public @NotNull ItemStack getItem(int index) {
            return items.get(index);
        }

        @Override
        public int size() {
            return items.size();
        }
    }

    public static class Serializer implements RecipeSerializer<JamRecipe> {
        private static final Codec<List<Ingredient>> INGREDIENTS_CODEC = Ingredient.CODEC_NONEMPTY.listOf().flatXmap(list -> {
            if (list.isEmpty() || list.size() > SmallCookingPotBlockEntity.MAX_INGREDIENTS) {
                return DataResult.error(() -> "Jam needs 1 to " + SmallCookingPotBlockEntity.MAX_INGREDIENTS + " ingredients");
            }
            return DataResult.success(list);
        }, DataResult::success);

        public static final MapCodec<JamRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                INGREDIENTS_CODEC.fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(JamRecipe::getResult),
                HexColor.CODEC.optionalFieldOf("color", 0xC8323C).forGetter(JamRecipe::getColor),
                Codec.intRange(20, 72000).optionalFieldOf("cooking_time", 600).forGetter(JamRecipe::getCookingTime),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(JamRecipe::getExperience)
        ).apply(instance, JamRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, JamRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.ingredients,
                ItemStack.STREAM_CODEC, JamRecipe::getResult,
                ByteBufCodecs.INT, JamRecipe::getColor,
                ByteBufCodecs.VAR_INT, JamRecipe::getCookingTime,
                ByteBufCodecs.FLOAT, JamRecipe::getExperience,
                JamRecipe::new
        );

        @Override
        public @NotNull MapCodec<JamRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, JamRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static final class HexColor {
        public static final Codec<Integer> CODEC = Codec.STRING.comapFlatMap(value -> {
            String hex = value.startsWith("#") ? value.substring(1) : value;
            try {
                return DataResult.success(Integer.parseInt(hex, 16) & 0xFFFFFF);
            } catch (NumberFormatException exception) {
                return DataResult.error(() -> "Invalid colour: " + value);
            }
        }, color -> String.format("#%06X", color & 0xFFFFFF));

        private HexColor() {
        }
    }
}
