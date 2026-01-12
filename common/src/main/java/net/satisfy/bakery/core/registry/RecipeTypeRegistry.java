package net.satisfy.bakery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.recipe.BakingStationRecipe;
import net.satisfy.bakery.core.recipe.BlankCakeInteractionRecipe;

import java.util.function.Supplier;

public class RecipeTypeRegistry {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Bakery.MOD_ID, Registries.RECIPE_SERIALIZER);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Bakery.MOD_ID, Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<BlankCakeInteractionRecipe>> BLANK_CAKE_INTERACTION_SERIALIZER = RECIPE_SERIALIZERS.register("blank_cake_interaction", BlankCakeInteractionRecipe.Serializer::new);
    public static final RegistrySupplier<RecipeType<BlankCakeInteractionRecipe>> BLANK_CAKE_INTERACTION_TYPE = RECIPE_TYPES.register("blank_cake_interaction", () -> new RecipeType<>() {});

    public static final RegistrySupplier<RecipeType<BakingStationRecipe>> BAKING_STATION_RECIPE_TYPE = create("baking_station");
    public static final RegistrySupplier<RecipeSerializer<BakingStationRecipe>> BAKING_STATION_RECIPE_SERIALIZER = create("baking_station", BakingStationRecipe.Serializer::new);

    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(name, serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(name, type);
    }

    public static void init() {
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }
}