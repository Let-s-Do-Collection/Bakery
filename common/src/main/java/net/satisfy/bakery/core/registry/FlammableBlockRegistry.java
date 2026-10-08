package net.satisfy.bakery.core.registry;

import net.satisfy.foundation.flammable.FoundationFlammables;

import static net.satisfy.bakery.core.registry.ObjectRegistry.*;

public class FlammableBlockRegistry {
    public static void init() {
        FoundationFlammables.wood(CABINET, DRAWER, WALL_CABINET, STREET_SIGN, BREADBOX, TRAY, BREAD_CRATE, WALL_DISPLAY);
    }
}
