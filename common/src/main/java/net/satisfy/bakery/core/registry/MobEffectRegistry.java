package net.satisfy.bakery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.effect.SugarRushEffect;
import net.satisfy.bakery.core.effect.VitalityEffect;

import java.util.function.Supplier;

public class MobEffectRegistry {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Bakery.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> VITALITY = registerEffect("vitality", VitalityEffect::new);
    public static final RegistrySupplier<MobEffect> SUGAR_RUSH = registerEffect("sugar_rush", SugarRushEffect::new);

    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static void init() {
        MOB_EFFECTS.register();
    }

    public static Holder<MobEffect> holder(RegistrySupplier<MobEffect> supplier) {
        ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, supplier.getId());
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(key);
    }
}