package net.satisfy.bakery.core.recipe;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CakeAnimation implements StringRepresentable {
    NONE("none", 0),
    DROP("drop", 8),
    SPREAD("spread", 12),
    SPRINKLE("sprinkle", 10),
    WOBBLE("wobble", 6);

    public static final StringRepresentable.EnumCodec<CakeAnimation> CODEC = StringRepresentable.fromEnum(CakeAnimation::values);
    public static final StreamCodec<ByteBuf, CakeAnimation> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(index -> values()[index], CakeAnimation::ordinal);

    private final String name;
    private final int duration;

    CakeAnimation(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public int duration() {
        return duration;
    }

    public static CakeAnimation byName(String name) {
        CakeAnimation animation = CODEC.byName(name);
        return animation == null ? NONE : animation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
