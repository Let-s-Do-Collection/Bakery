package net.satisfy.bakery.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * One visual piece of a custom cake: a shared grayscale model part ({@code style}) tinted with {@code color}.
 * Styles resolve to {@code bakery:block/custom_cake/<layer>/<style path>}.
 */
public record CakePart(ResourceLocation style, int color) {
    public static final Codec<Integer> COLOR_CODEC = Codec.STRING.comapFlatMap(CakePart::parseColor, color -> String.format("#%06X", color & 0xFFFFFF));

    public static final Codec<CakePart> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("style").forGetter(CakePart::style),
            COLOR_CODEC.optionalFieldOf("color", 0xFFFFFF).forGetter(CakePart::color)
    ).apply(instance, CakePart::new));

    public static final StreamCodec<ByteBuf, CakePart> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, CakePart::style,
            ByteBufCodecs.INT, CakePart::color,
            CakePart::new
    );

    private static DataResult<Integer> parseColor(String value) {
        String hex = value.startsWith("#") ? value.substring(1) : value;
        try {
            return DataResult.success(Integer.parseInt(hex, 16) & 0xFFFFFF);
        } catch (NumberFormatException exception) {
            return DataResult.error(() -> "Invalid cake color: " + value);
        }
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Style", style.toString());
        tag.putInt("Color", color);
        return tag;
    }

    public static @Nullable CakePart load(CompoundTag tag) {
        ResourceLocation style = ResourceLocation.tryParse(tag.getString("Style"));
        return style == null ? null : new CakePart(style, tag.getInt("Color"));
    }
}
