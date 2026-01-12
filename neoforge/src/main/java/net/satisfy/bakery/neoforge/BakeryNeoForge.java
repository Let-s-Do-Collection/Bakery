package net.satisfy.bakery.neoforge;

import java.util.Objects;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.CompostableRegistry;
import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;

@Mod(Bakery.MOD_ID)
public class BakeryNeoForge {
    public BakeryNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, BakeryNeoForgeConfig.COMMON_CONFIG);

        Objects.requireNonNull(modContainer.getEventBus()).addListener((ModConfigEvent.Loading event) -> {
            if (event.getConfig().getSpec() == BakeryNeoForgeConfig.COMMON_CONFIG) {
                BakeryNeoForgeConfig.sync();
            }
        });

        modContainer.getEventBus().addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getSpec() == BakeryNeoForgeConfig.COMMON_CONFIG) {
                BakeryNeoForgeConfig.sync();
            }
        });

        modContainer.getEventBus().addListener(this::commonSetup);
        Bakery.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
    }
}
