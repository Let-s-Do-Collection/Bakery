package net.satisfy.bakery.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;

import java.util.Optional;

public record Filling(ResourceLocation source, int color) {
    public static final int CREAM = 0xF5F0E6;
    public static final TagKey<Item> MILK = TagKey.create(Registries.ITEM, Bakery.identifier("milk"));

    public static final Codec<Filling> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("source").forGetter(Filling::source),
            Codec.INT.fieldOf("color").forGetter(Filling::color)
    ).apply(instance, Filling::new));

    public static final StreamCodec<ByteBuf, Filling> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, Filling::source,
            ByteBufCodecs.INT, Filling::color,
            Filling::new
    );

    public static Optional<Filling> of(Level level, ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        ResourceLocation source = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (stack.is(MILK)) {
            return Optional.of(new Filling(source, CREAM));
        }
        return level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get()).stream()
                .map(holder -> holder.value())
                .filter(recipe -> recipe.getResult().is(stack.getItem()))
                .findFirst()
                .map(recipe -> new Filling(source, recipe.getColor()));
    }

    public Item sourceItem() {
        return BuiltInRegistries.ITEM.get(source);
    }
}
