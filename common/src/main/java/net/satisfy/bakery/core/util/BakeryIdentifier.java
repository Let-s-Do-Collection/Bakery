package net.satisfy.bakery.core.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.Bakery;

public class BakeryIdentifier {

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(Bakery.MOD_ID, name);
    }

    @SuppressWarnings("unused")
    public static String asString(String path) {
        return (Bakery.MOD_ID + ":" + path);
    }
}
