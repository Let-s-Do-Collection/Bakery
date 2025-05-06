package net.satisfy.bakery.core.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PacketHandler {
    public static final ResourceLocation SET_SIGN_TEXT = new ResourceLocation("bakery", "set_sign_text");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), SET_SIGN_TEXT, (buf, context) -> {
            SetStreetSignTextPacket packet = SetStreetSignTextPacket.decode(buf);
            context.queue(() -> SetStreetSignTextPacket.handle(packet, (ServerPlayer) context.getPlayer()));
        });
    }

    public static void sendToServer(SetStreetSignTextPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(io.netty.buffer.Unpooled.buffer());
        SetStreetSignTextPacket.encode(packet, buf);
        NetworkManager.sendToServer(SET_SIGN_TEXT, buf);
    }
}
