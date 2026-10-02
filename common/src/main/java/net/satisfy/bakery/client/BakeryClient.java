package net.satisfy.bakery.client;

import net.satisfy.foundation.storage.StorageBlockEntityRenderer;
import net.satisfy.foundation.storage.StorageTypeRenderer;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.client.gui.StreetSignEditGui;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.satisfy.bakery.client.gui.overlay.BakerStationInfoProvider;
import net.satisfy.bakery.client.gui.overlay.JamPotInfoProvider;
import net.satisfy.bakery.core.recipe.Filling;
import net.satisfy.bakery.core.registry.DataComponentRegistry;
import net.satisfy.bakery.client.renderer.block.*;
import net.satisfy.foundation.overlay.BlockInfoOverlay;
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
        BlockInfoOverlay.init();
        BlockInfoOverlay.registerProvider(new BakerStationInfoProvider());
        BlockInfoOverlay.registerProvider(new JamPotInfoProvider());
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> {
            if (tintIndex != 1) {
                return -1;
            }
            Filling filling = stack.get(DataComponentRegistry.FILLING.get());
            int fallback = stack.is(ObjectRegistry.CORNET.get()) ? Filling.CREAM : 0xC8323C;
            return 0xFF000000 | (filling != null ? filling.color() : fallback);
        }, ObjectRegistry.CORNET.get(), ObjectRegistry.JAM_ROLL.get());
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.is(ObjectRegistry.CORNET_SHELL.get()) || stack.is(ObjectRegistry.SPONGE_SHEET.get())) {
                lines.add(Math.min(1, lines.size()), Component.translatable("tooltip.bakery.unfilled").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xA0A0A0))));
            }
            Filling filling = stack.get(DataComponentRegistry.FILLING.get());
            if (filling != null) {
                lines.add(Math.min(1, lines.size()), Component.translatable("tooltip.bakery.filling", filling.sourceItem().getDescription()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xA0A0A0))));
            }
            String cook = stack.get(DataComponentRegistry.PERFECT_JAM.get());
            if (cook != null && !cook.isEmpty()) {
                int index = lines.size();
                for (int line = 0; line < lines.size(); line++) {
                    if (lines.get(line).getContents() instanceof TranslatableContents contents && contents.getKey().equals("tooltip.foundation.canbeplaced")) {
                        index = line + 1;
                    }
                }
                lines.add(index, Component.empty());
                lines.add(index + 1, Component.translatable("tooltip.bakery.jam_cook", cook).withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0xE3A6A0))));
            }
        });
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
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BLANK_CAKE_BLOCK_ENTITY.get(), context -> new BlankCakeRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.CAKE_CANDLE_BLOCK_ENTITY.get(), context -> new CakeCandleRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BAKER_STATION_BLOCK_ENTITY.get(), context -> new BakerStationRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.SMALL_COOKING_POT_BLOCK_ENTITY.get(), JamPotRenderer::new);
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);
    }
}
