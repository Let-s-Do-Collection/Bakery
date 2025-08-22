package net.satisfy.bakery.core.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import net.satisfy.farm_and_charm.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class BakingStationRecipe implements Recipe<RecipeInput> {

    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public BakingStationRecipe(NonNullList<Ingredient> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return GeneralUtil.matchesRecipe(recipeInput, inputs, 1, 3);
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return null;
    }

    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    public @NotNull ResourceLocation getId() {
        return RecipeTypeRegistry.BAKING_STATION_RECIPE_TYPE.getId();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.BAKING_STATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.BAKING_STATION_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<BakingStationRecipe> {

        public static final StreamCodec<RegistryFriendlyByteBuf, BakingStationRecipe> STREAM_CODEC = StreamCodec.of(BakingStationRecipe.Serializer::toNetwork, BakingStationRecipe.Serializer::fromNetwork);
        private static final MapCodec<BakingStationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> {
            return instance.group(Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
                Ingredient[] ingredients = list.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                if (ingredients.length == 0) {
                    return DataResult.error(() -> {
                        return "No ingredients for Baking Station recipe";
                    });
                } else {
                    return ingredients.length > 3 ? DataResult.error(() -> {
                        return "Too many ingredients for Baking Station recipe";
                    }) : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                }
            }, DataResult::success).forGetter(bakingStationRecipe -> bakingStationRecipe.inputs), ItemStack.STRICT_CODEC.fieldOf("result").forGetter(bakingStationRecipe -> bakingStationRecipe.output)).apply(instance, BakingStationRecipe::new);
        });

        public static @NotNull BakingStationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int i = buf.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);
            nonNullList.replaceAll((ingredient) -> {
                return Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            });
            ItemStack itemStack = ItemStack.STREAM_CODEC.decode(buf);
            return new BakingStationRecipe(nonNullList, itemStack);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, BakingStationRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public MapCodec<BakingStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BakingStationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}