package net.satisfy.bakery.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class BlankCakeInteractionRecipe implements Recipe<BlankCakeInteractionInput> {
    private final BlankCakeStage stage;
    private final Ingredient ingredient;
    private final Result result;
    private final int priority;

    public BlankCakeInteractionRecipe(BlankCakeStage stage, Ingredient ingredient, Result result, int priority) {
        this.stage = stage;
        this.ingredient = ingredient;
        this.result = result;
        this.priority = priority;
    }

    public BlankCakeStage stage() {
        return stage;
    }

    public Ingredient ingredient() {
        return ingredient;
    }

    public Result result() {
        return result;
    }

    public int priority() {
        return priority;
    }

    public boolean matchesStage(BlankCakeStage currentStage) {
        return stage == currentStage;
    }

    @Override
    public boolean matches(BlankCakeInteractionInput input, Level level) {
        return ingredient.test(input.stack());
    }

    @Override
    public @NotNull ItemStack assemble(BlankCakeInteractionInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.BLANK_CAKE_INTERACTION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.BLANK_CAKE_INTERACTION_TYPE.get();
    }

    public static final class Serializer implements RecipeSerializer<BlankCakeInteractionRecipe> {
        public static final MapCodec<BlankCakeInteractionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.xmap(BlankCakeStage::valueOf, BlankCakeStage::name).fieldOf("stage").forGetter(BlankCakeInteractionRecipe::stage),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(BlankCakeInteractionRecipe::ingredient),
                Result.CODEC.fieldOf("result").forGetter(BlankCakeInteractionRecipe::result),
                Codec.INT.optionalFieldOf("priority", 1000).forGetter(BlankCakeInteractionRecipe::priority)
        ).apply(instance, BlankCakeInteractionRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, BlankCakeInteractionRecipe> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull BlankCakeInteractionRecipe decode(RegistryFriendlyByteBuf buffer) {
                BlankCakeStage stage = BlankCakeStage.valueOf(buffer.readUtf());
                Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                Result result = ResultStream.CODEC.decode(buffer);
                int priority = buffer.readVarInt();
                return new BlankCakeInteractionRecipe(stage, ingredient, result, priority);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, BlankCakeInteractionRecipe value) {
                buffer.writeUtf(value.stage().name());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, value.ingredient());
                ResultStream.CODEC.encode(buffer, value.result());
                buffer.writeVarInt(value.priority());
            }
        };

        @Override
        public @NotNull MapCodec<BlankCakeInteractionRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BlankCakeInteractionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public record Result(ResourceLocation setBlock, BlankCakeStage setStage, ResourceLocation giveItem, ResourceLocation sound, boolean consumeOne, boolean particles, int cooldownTicks, CakeAnimation animation) {
            public static final Codec<Result> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("set_block").forGetter(r -> Optional.ofNullable(r.setBlock)),
                    Codec.STRING.xmap(BlankCakeStage::valueOf, BlankCakeStage::name).optionalFieldOf("set_stage").forGetter(r -> Optional.ofNullable(r.setStage)),
                    ResourceLocation.CODEC.optionalFieldOf("give_item").forGetter(r -> Optional.ofNullable(r.giveItem)),
                    ResourceLocation.CODEC.optionalFieldOf("sound").forGetter(r -> Optional.ofNullable(r.sound)),
                    Codec.BOOL.optionalFieldOf("consume_one", false).forGetter(Result::consumeOne),
                    Codec.BOOL.optionalFieldOf("particles", true).forGetter(Result::particles),
                    Codec.INT.optionalFieldOf("cooldown_ticks", 0).forGetter(Result::cooldownTicks),
                    CakeAnimation.CODEC.optionalFieldOf("animation", CakeAnimation.SPREAD).forGetter(Result::animation)
            ).apply(instance, (setBlock, setStage, giveItem, sound, consumeOne, particles, cooldownTicks, animation) -> new Result(
                    setBlock.orElse(null),
                    setStage.orElse(null),
                    giveItem.orElse(null),
                    sound.orElse(null),
                    consumeOne,
                    particles,
                    cooldownTicks,
                    animation
            )));

    }

    private static final class ResultStream {
        private static final StreamCodec<RegistryFriendlyByteBuf, Result> CODEC = new StreamCodec<>() {
            @Override
            public @NotNull Result decode(RegistryFriendlyByteBuf buffer) {
                ResourceLocation setBlock = buffer.readBoolean() ? ResourceLocation.STREAM_CODEC.decode(buffer) : null;
                BlankCakeStage setStage = buffer.readBoolean() ? BlankCakeStage.valueOf(buffer.readUtf()) : null;
                ResourceLocation giveItem = buffer.readBoolean() ? ResourceLocation.STREAM_CODEC.decode(buffer) : null;
                ResourceLocation sound = buffer.readBoolean() ? ResourceLocation.STREAM_CODEC.decode(buffer) : null;
                boolean consumeOne = ByteBufCodecs.BOOL.decode(buffer);
                boolean particles = ByteBufCodecs.BOOL.decode(buffer);
                int cooldownTicks = ByteBufCodecs.INT.decode(buffer);
                CakeAnimation animation = CakeAnimation.STREAM_CODEC.decode(buffer);
                return new Result(setBlock, setStage, giveItem, sound, consumeOne, particles, cooldownTicks, animation);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, Result value) {
                buffer.writeBoolean(value.setBlock() != null);
                if (value.setBlock() != null) {
                    ResourceLocation.STREAM_CODEC.encode(buffer, value.setBlock());
                }

                buffer.writeBoolean(value.setStage() != null);
                if (value.setStage() != null) {
                    buffer.writeUtf(value.setStage().name());
                }

                buffer.writeBoolean(value.giveItem() != null);
                if (value.giveItem() != null) {
                    ResourceLocation.STREAM_CODEC.encode(buffer, value.giveItem());
                }

                buffer.writeBoolean(value.sound() != null);
                if (value.sound() != null) {
                    ResourceLocation.STREAM_CODEC.encode(buffer, value.sound());
                }

                ByteBufCodecs.BOOL.encode(buffer, value.consumeOne());
                ByteBufCodecs.BOOL.encode(buffer, value.particles());
                ByteBufCodecs.INT.encode(buffer, value.cooldownTicks());
                CakeAnimation.STREAM_CODEC.encode(buffer, value.animation());
            }
        };
    }
}
