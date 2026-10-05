package net.satisfy.bakery.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class ClientPlayerName {
    private ClientPlayerName() {
    }

    public static Component get() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null ? minecraft.player.getName() : Component.translatable("tooltip.bakery.baked_sweet_dough.you");
    }
}
