package net.satisfy.bakery;

import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.core.event.CommonEvents;
import net.satisfy.bakery.core.network.PacketHandler;
import net.satisfy.bakery.core.registry.*;
import net.satisfy.foundation.rarity.FoundationRarities;
import net.satisfy.foundation.rarity.FoundationRarity;

public class Bakery {
    public static final String MOD_ID = "bakery";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        MobEffectRegistry.init();
        ObjectRegistry.init();
        FlammableBlockRegistry.init();
        EntityTypeRegistry.init();
        RecipeTypeRegistry.init();
        DataComponentRegistry.init();
        PacketHandler.init();
        CommonEvents.init();
        TabRegistry.init();
        SoundEventRegistry.init();
        LifecycleEvent.SETUP.register(Bakery::registerRarities);
    }

    private static void registerRarities() {
        FoundationRarities.register(ObjectRegistry.BAKERY_BANNER.get(), FoundationRarity.LEGENDARY);
    }
}
