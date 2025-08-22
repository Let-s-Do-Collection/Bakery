package net.satisfy.bakery.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.bakery.core.util.BakeryIdentifier;

import java.util.Set;

public class StorageTypeRegistry {
    public static final ResourceLocation CAKE_STAND = BakeryIdentifier.identifier("cake_stand");
    public static final ResourceLocation TRAY = BakeryIdentifier.identifier("tray");
    public static final ResourceLocation BREADBOX = BakeryIdentifier.identifier("breadbox");
    public static final ResourceLocation CAKE_DISPLAY = BakeryIdentifier.identifier("cake_display");
    public static final ResourceLocation CUPCAKE_DISPLAY = BakeryIdentifier.identifier("cupcake_display");
    public static final ResourceLocation WALL_DISPLAY = BakeryIdentifier.identifier("wall_display");

    public static Set<Block> registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CAKE_STAND.get());
        blocks.add(ObjectRegistry.TRAY.get());
        blocks.add(ObjectRegistry.BREADBOX.get());
        blocks.add(ObjectRegistry.CAKE_DISPLAY.get());
        blocks.add(ObjectRegistry.CUPCAKE_DISPLAY.get());
        blocks.add(ObjectRegistry.WALL_DISPLAY.get());
        return blocks;
    }
}