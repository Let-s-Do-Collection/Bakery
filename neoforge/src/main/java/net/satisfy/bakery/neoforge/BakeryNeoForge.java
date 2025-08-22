package net.satisfy.bakery.neoforge;

import dev.architectury.platform.hooks.EventBusesHooks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.CompostableRegistry;
import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;

@Mod(Bakery.MOD_ID)
public class BakeryNeoForge {
    public BakeryNeoForge(IEventBus modEventBus, final ModContainer modContainer) {
        EventBusesHooks.whenAvailable(Bakery.MOD_ID, IEventBus::start);
        Bakery.init();
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, BakeryNeoForgeConfig.COMMON_CONFIG);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
    }
}
