package net.satisfy.bakery;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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
        CompostableRegistry.registerCompostable();
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
        registerRarity(FoundationRarity.COMMON,
                ObjectRegistry.CAKE_DOUGH, ObjectRegistry.CORNET_SHELL, ObjectRegistry.SPONGE_SHEET, ObjectRegistry.SWEET_DOUGH, ObjectRegistry.BAKED_SWEET_DOUGH,
                ObjectRegistry.BREAD, ObjectRegistry.CRUSTY_BREAD, ObjectRegistry.TOAST, ObjectRegistry.BUN,
                ObjectRegistry.JAR, ObjectRegistry.ROLLING_PIN, ObjectRegistry.BREAD_KNIFE, ObjectRegistry.SMALL_COOKING_POT_ITEM,
                ObjectRegistry.KITCHEN_SINK, ObjectRegistry.BAKER_STATION, ObjectRegistry.BRICK_COUNTER, ObjectRegistry.CABINET, ObjectRegistry.DRAWER, ObjectRegistry.WALL_CABINET,
                ObjectRegistry.IRON_BENCH, ObjectRegistry.IRON_CHAIR, ObjectRegistry.IRON_TABLE, ObjectRegistry.STREET_SIGN,
                ObjectRegistry.BREADBOX, ObjectRegistry.TRAY, ObjectRegistry.BREAD_CRATE, ObjectRegistry.WALL_DISPLAY, ObjectRegistry.CAKE_STAND);
        registerRarity(FoundationRarity.UNCOMMON,
                ObjectRegistry.BAGUETTE, ObjectRegistry.BRAIDED_BREAD, ObjectRegistry.CROISSANT, ObjectRegistry.WAFFLE,
                ObjectRegistry.SANDWICH, ObjectRegistry.VEGETABLE_SANDWICH, ObjectRegistry.BREAD_WITH_JAM,
                ObjectRegistry.STRAWBERRY_JAM, ObjectRegistry.GLOWBERRY_JAM, ObjectRegistry.SWEETBERRY_JAM, ObjectRegistry.CHOCOLATE_JAM, ObjectRegistry.APPLE_JAM,
                ObjectRegistry.STRAWBERRY_GLAZED_COOKIE, ObjectRegistry.SWEETBERRY_GLAZED_COOKIE, ObjectRegistry.CHOCOLATE_GLAZED_COOKIE,
                ObjectRegistry.CAKE_DISPLAY, ObjectRegistry.CUPCAKE_DISPLAY, ObjectRegistry.CHOCOLATE_BOX);
        registerRarity(FoundationRarity.RARE,
                ObjectRegistry.GRILLED_SALMON_SANDWICH, ObjectRegistry.GRILLED_BACON_SANDWICH,
                ObjectRegistry.STRAWBERRY_CUPCAKE, ObjectRegistry.SWEETBERRY_CUPCAKE, ObjectRegistry.APPLE_CUPCAKE,
                ObjectRegistry.CORNET, ObjectRegistry.JAM_ROLL, ObjectRegistry.CHOCOLATE_TRUFFLE, ObjectRegistry.MISSLILITU_BISCUIT,
                ObjectRegistry.STRAWBERRY_CAKE_SLICE, ObjectRegistry.SWEETBERRY_CAKE_SLICE, ObjectRegistry.CHOCOLATE_CAKE_SLICE, ObjectRegistry.CHOCOLATE_GATEAU_SLICE,
                ObjectRegistry.BUNDT_CAKE_SLICE, ObjectRegistry.LINZER_TART_SLICE, ObjectRegistry.APPLE_PIE_SLICE, ObjectRegistry.GLOWBERRY_PIE_SLICE,
                ObjectRegistry.CHOCOLATE_TART_SLICE, ObjectRegistry.PUDDING_SLICE);
        registerRarity(FoundationRarity.EPIC,
                ObjectRegistry.STRAWBERRY_CAKE, ObjectRegistry.SWEETBERRY_CAKE, ObjectRegistry.CHOCOLATE_CAKE, ObjectRegistry.CHOCOLATE_GATEAU,
                ObjectRegistry.BUNDT_CAKE, ObjectRegistry.LINZER_TART, ObjectRegistry.APPLE_PIE, ObjectRegistry.GLOWBERRY_TART,
                ObjectRegistry.CHOCOLATE_TART, ObjectRegistry.PUDDING);
        registerRarity(FoundationRarity.LEGENDARY, ObjectRegistry.BAKERY_BANNER);
        for (Item item : BuiltInRegistries.ITEM) {
            if (MOD_ID.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace()) && FoundationRarities.get(new ItemStack(item)) == null) {
                FoundationRarities.register(item, FoundationRarity.COMMON);
            }
        }
    }

    @SafeVarargs
    private static void registerRarity(FoundationRarity rarity, RegistrySupplier<? extends ItemLike>... entries) {
        for (RegistrySupplier<? extends ItemLike> entry : entries) {
            FoundationRarities.register(entry.get(), rarity);
        }
    }
}
