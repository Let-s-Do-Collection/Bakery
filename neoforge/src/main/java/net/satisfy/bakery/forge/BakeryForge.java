package net.satisfy.bakery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.CompostableRegistry;
import net.satisfy.bakery.forge.core.config.BakeryForgeConfig;

@Mod(Bakery.MOD_ID)
public class BakeryForge {
    public BakeryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Bakery.MOD_ID, modEventBus);
        Bakery.init();
        BakeryForgeConfig.loadConfig(BakeryForgeConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("bakery.toml").toString());
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
    }
}
