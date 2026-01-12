package net.satisfy.bakery.core.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.effect.SugarRushEffect;
import net.satisfy.bakery.core.effect.VitalityEffect;

import java.util.function.Supplier;

public class MobEffectRegistry {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Bakery.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR =
            MOB_EFFECTS.getRegistrar();

    public static final RegistrySupplier<MobEffect> VITALITY;
    public static final RegistrySupplier<MobEffect> SUGAR_RUSH;

    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        if (Platform.isNeoForge()) {
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(Bakery.identifier(name), effect);
    }

    public static void init() {
        MOB_EFFECTS.register();
    }

    public static Holder<MobEffect> holder(RegistrySupplier<MobEffect> supplier) {
        return BuiltInRegistries.MOB_EFFECT
                .getResourceKey(supplier.get())
                .flatMap(BuiltInRegistries.MOB_EFFECT::getHolder)
                .orElseThrow();
    }

    static {
        VITALITY = registerEffect("vitality", VitalityEffect::new);
        SUGAR_RUSH = registerEffect("sugar_rush", SugarRushEffect::new);
    }
}