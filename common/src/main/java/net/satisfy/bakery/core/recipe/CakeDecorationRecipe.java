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

public record CakeDecorationRecipe(CakeLayer layer, Ingredient ingredient, CakePart part, int uses, CakeAnimation animation, boolean consume, Optional<ResourceLocation> remainder, Optional<ResourceLocation> sound) implements Recipe<BlankCakeInteractionInput> {

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
        return RecipeTypeRegistry.CAKE_DECORATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.CAKE_DECORATION_TYPE.get();
    }

    public static final class Serializer implements RecipeSerializer<CakeDecorationRecipe> {
        public static final MapCodec<CakeDecorationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                CakeLayer.CODEC.fieldOf("layer").forGetter(CakeDecorationRecipe::layer),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CakeDecorationRecipe::ingredient),
                CakePart.CODEC.fieldOf("part").forGetter(CakeDecorationRecipe::part),
                Codec.intRange(1, 16).optionalFieldOf("uses", 1).forGetter(CakeDecorationRecipe::uses),
                CakeAnimation.CODEC.optionalFieldOf("animation", CakeAnimation.DROP).forGetter(CakeDecorationRecipe::animation),
                Codec.BOOL.optionalFieldOf("consume", true).forGetter(CakeDecorationRecipe::consume),
                ResourceLocation.CODEC.optionalFieldOf("remainder").forGetter(CakeDecorationRecipe::remainder),
                ResourceLocation.CODEC.optionalFieldOf("sound").forGetter(CakeDecorationRecipe::sound)
        ).apply(instance, CakeDecorationRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CakeDecorationRecipe> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull CakeDecorationRecipe decode(RegistryFriendlyByteBuf buffer) {
                CakeLayer layer = CakeLayer.STREAM_CODEC.decode(buffer);
                Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                CakePart part = CakePart.STREAM_CODEC.decode(buffer);
                int uses = buffer.readVarInt();
                CakeAnimation animation = CakeAnimation.STREAM_CODEC.decode(buffer);
                boolean consume = buffer.readBoolean();
                Optional<ResourceLocation> remainder = ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer);
                Optional<ResourceLocation> sound = ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer);
                return new CakeDecorationRecipe(layer, ingredient, part, uses, animation, consume, remainder, sound);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, CakeDecorationRecipe value) {
                CakeLayer.STREAM_CODEC.encode(buffer, value.layer());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, value.ingredient());
                CakePart.STREAM_CODEC.encode(buffer, value.part());
                buffer.writeVarInt(value.uses());
                CakeAnimation.STREAM_CODEC.encode(buffer, value.animation());
                buffer.writeBoolean(value.consume());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.remainder());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.sound());
            }
        };

        @Override
        public @NotNull MapCodec<CakeDecorationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, CakeDecorationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
