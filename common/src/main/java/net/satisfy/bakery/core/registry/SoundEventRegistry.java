package net.satisfy.bakery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.util.BakeryIdentifier;

public class SoundEventRegistry {

    public static void init() {}

    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Bakery.MOD_ID, Registries.SOUND_EVENT).getRegistrar();

    public static final RegistrySupplier<SoundEvent> COOKING_POT_HIT = create("cooking_pot_hit");
    public static final RegistrySupplier<SoundEvent> CAKE_CUT = create("cake_cut");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = create("drawer_open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = create("drawer_close");
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = create("cabinet_open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = create("cabinet_close");

    private static RegistrySupplier<SoundEvent> create(String name) {
        ResourceLocation id = BakeryIdentifier.identifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
