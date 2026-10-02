package net.satisfy.bakery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.recipe.Filling;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;

public class DataComponentRegistry {
    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Bakery.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<String>> PERFECT_JAM = COMPONENTS.register("perfect_jam",
            () -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final RegistrySupplier<DataComponentType<Filling>> FILLING = COMPONENTS.register("filling",
            () -> DataComponentType.<Filling>builder().persistent(Filling.CODEC).networkSynchronized(Filling.STREAM_CODEC).build());

    public static void init() {
        COMPONENTS.register();
    }
}
