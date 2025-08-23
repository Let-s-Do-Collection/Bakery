package net.satisfy.bakery.core.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.satisfy.bakery.core.block.entity.StreetSignBlockEntity;

import java.util.List;

public record SetStreetSignTextPacket(BlockPos pos, List<String> texts) implements CustomPacketPayload {

    public static Type<SetStreetSignTextPacket> TYPE = new Type<>(PacketHandler.SET_SIGN_TEXT);

    public static final StreamCodec<RegistryFriendlyByteBuf, SetStreetSignTextPacket> STREAM_CODEC =
            StreamCodec.of(SetStreetSignTextPacket::toNetwork, SetStreetSignTextPacket::fromNetwork);

    public static void toNetwork(RegistryFriendlyByteBuf buf, SetStreetSignTextPacket msg) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.texts.size());
        for (String text : msg.texts) {
            buf.writeUtf(text);
        }
    }

    public static SetStreetSignTextPacket fromNetwork(RegistryFriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int size = buf.readInt();
        List<String> texts = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            texts.add(buf.readUtf(50));
        }
        return new SetStreetSignTextPacket(pos, texts);
    }

    public static void handle(SetStreetSignTextPacket msg, ServerPlayer player) {
        Level level = player.level();
        if (level.isLoaded(msg.pos)) {
            BlockEntity entity = level.getBlockEntity(msg.pos);
            if (entity instanceof StreetSignBlockEntity signEntity) {
                for (int i = 0; i < msg.texts.size() && i < 4; i++) {
                    signEntity.setText(i, Component.literal(msg.texts.get(i)));
                }
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
