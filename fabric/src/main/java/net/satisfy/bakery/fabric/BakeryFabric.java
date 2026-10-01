package net.satisfy.bakery.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.CompostableRegistry;
import net.satisfy.bakery.fabric.core.config.BakeryFabricConfig;

public class BakeryFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(BakeryFabricConfig.class, GsonConfigSerializer::new);
        Bakery.init();

        if (FabricLoader.getInstance().isModLoaded("farm_and_charm")) {
            CompostableRegistry.registerCompostable();
        }

        FabricLoader.getInstance().getModContainer(Bakery.MOD_ID).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Bakery.MOD_ID, "vanilla_blend"),
                        container,
                        Component.translatable("pack.bakery.vanilla_blend"),
                        ResourcePackActivationType.NORMAL));
    }
}
