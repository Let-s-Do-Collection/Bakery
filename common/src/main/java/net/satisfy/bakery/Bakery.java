package net.satisfy.bakery;

import net.satisfy.bakery.core.event.CommonEvents;
import net.satisfy.bakery.core.network.PacketHandler;
import net.satisfy.bakery.core.registry.*;

public class Bakery {
    public static final String MOD_ID = "bakery";

    public static void init() {
        EntityTypeRegistry.init();
        ObjectRegistry.init();
        RecipeTypeRegistry.init();
        PacketHandler.init();
        CommonEvents.init();
        TabRegistry.init();
        SoundEventRegistry.init();
    }
}

