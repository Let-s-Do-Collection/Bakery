package net.satisfy.bakery;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.core.event.CommonEvents;
import net.satisfy.bakery.core.network.PacketHandler;
import net.satisfy.bakery.core.registry.*;

public class Bakery {
    public static final String MOD_ID = "bakery";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        MobEffectRegistry.init();
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        RecipeTypeRegistry.init();
        PacketHandler.init();
        CommonEvents.init();
        TabRegistry.init();
        SoundEventRegistry.init();
    }
}

