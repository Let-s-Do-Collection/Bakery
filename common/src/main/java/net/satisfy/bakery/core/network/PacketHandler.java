package net.satisfy.bakery.core.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.network.packet.SetStreetSignTextPacket;

public class PacketHandler {
    public static final ResourceLocation SET_SIGN_TEXT = Bakery.identifier( "set_sign_text");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), SetStreetSignTextPacket.TYPE, SetStreetSignTextPacket.STREAM_CODEC, (packet, context) -> {
            context.queue(() -> SetStreetSignTextPacket.handle(packet, (ServerPlayer) context.getPlayer()));
        });
    }

    public static void sendToServer(SetStreetSignTextPacket packet) {
        NetworkManager.sendToServer(packet);
    }
}
