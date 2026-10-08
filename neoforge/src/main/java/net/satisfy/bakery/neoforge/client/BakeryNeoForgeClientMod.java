package net.satisfy.bakery.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.satisfy.bakery.Bakery;

@Mod(value = Bakery.MOD_ID, dist = Dist.CLIENT)
public class BakeryNeoForgeClientMod {
    public BakeryNeoForgeClientMod(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
