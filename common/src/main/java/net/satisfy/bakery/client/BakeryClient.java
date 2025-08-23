package net.satisfy.bakery.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.client.gui.StreetSignEditGui;
import net.satisfy.bakery.client.renderer.block.*;
import net.satisfy.bakery.core.block.entity.StreetSignBlockEntity;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.StorageTypeRegistry;

@Environment(EnvType.CLIENT)
public class BakeryClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                ObjectRegistry.CAKE_STAND.get(), ObjectRegistry.IRON_TABLE.get(), ObjectRegistry.IRON_CHAIR.get(), ObjectRegistry.JAR.get(), ObjectRegistry.SWEETBERRY_JAM.get(), ObjectRegistry.CHOCOLATE_JAM.get(),
                ObjectRegistry.STRAWBERRY_JAM.get(), ObjectRegistry.GLOWBERRY_JAM.get(), ObjectRegistry.APPLE_JAM.get(), ObjectRegistry.CAKE_DISPLAY.get(), ObjectRegistry.SMALL_COOKING_POT.get(),
                ObjectRegistry.IRON_BENCH.get(), ObjectRegistry.BAKER_STATION.get(), ObjectRegistry.TRAY.get()
        );

        registerStorageType();
        registerBlockEntityRenderer();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.CAKE_STAND.get());
    }

    public static void openStreetSignScreen(StreetSignBlockEntity entity) {
        Minecraft.getInstance().setScreen(new StreetSignEditGui(entity));
    }

    public static void preInitClient() {
        registerEntityModelLayer();
    }

    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer) {
       StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void registerStorageType() {
        registerStorageType(StorageTypeRegistry.CAKE_STAND, new CakeStandRenderer());
        registerStorageType(StorageTypeRegistry.TRAY, new TrayRenderer());
        registerStorageType(StorageTypeRegistry.BREADBOX, new BreadBoxRenderer());
        registerStorageType(StorageTypeRegistry.CAKE_DISPLAY, new CakeDisplayRenderer());
        registerStorageType(StorageTypeRegistry.CUPCAKE_DISPLAY, new CupcakeDisplayRenderer());
        registerStorageType(StorageTypeRegistry.WALL_DISPLAY, new WallDisplayRenderer());
    }

    public static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BAKERY_BANNER.get(), CompletionistBannerRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.STORAGE_ENTITY.get(), context -> new StorageBlockEntityRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.STREET_SIGN_BLOCK_ENTITY.get(), context -> new StreetSignBlockRenderer());
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);
    }
}
