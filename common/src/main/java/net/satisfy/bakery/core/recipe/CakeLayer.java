package net.satisfy.bakery.core.recipe;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CakeLayer implements StringRepresentable {
    FILLING("filling"),
    GLAZE("glaze"),
    TOPPING("topping");

    public static final StringRepresentable.EnumCodec<CakeLayer> CODEC = StringRepresentable.fromEnum(CakeLayer::values);
    public static final StreamCodec<ByteBuf, CakeLayer> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(index -> values()[index], CakeLayer::ordinal);

    private final String name;

    CakeLayer(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
