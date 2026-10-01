package net.satisfy.bakery.neoforge;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.registry.CompostableRegistry;
import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;
import org.jetbrains.annotations.Nullable;

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
        modContainer.getEventBus().addListener(BakeryNeoForge::addBuiltinPacks);
        Bakery.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;
        Path root = findBuiltinPack("vanilla_blend");
        if (root == null) return;
        PackLocationInfo info = new PackLocationInfo("mod/" + Bakery.MOD_ID + ":vanilla_blend",
                Component.translatable("pack.bakery.vanilla_blend"), PackSource.BUILT_IN, Optional.empty());
        Pack pack = Pack.readMetaAndCreate(info, new PathPackResources.PathResourcesSupplier(root),
                PackType.CLIENT_RESOURCES, new PackSelectionConfig(false, Pack.Position.TOP, false));
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    /**
     * Built-in packs live in the common module. In production they are shadowed into the mod jar, but in
     * the dev environment the common resources are a separate classpath entry, so fall back to the classpath.
     */
    private static @Nullable Path findBuiltinPack(String name) {
        Path modPath = ModList.get().getModFileById(Bakery.MOD_ID).getFile().findResource("resourcepacks", name);
        if (Files.exists(modPath.resolve("pack.mcmeta"))) return modPath;
        try {
            URL url = BakeryNeoForge.class.getResource("/resourcepacks/" + name + "/pack.mcmeta");
            if (url != null && "file".equals(url.getProtocol())) return Path.of(url.toURI()).getParent();
        } catch (URISyntaxException ignored) {
        }
        return null;
    }
}
