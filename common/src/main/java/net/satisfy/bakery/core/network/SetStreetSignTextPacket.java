package net.satisfy.bakery.core.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.satisfy.bakery.core.block.entity.StreetSignBlockEntity;

import java.util.List;

public class SetStreetSignTextPacket {
    private final BlockPos pos;
    private final List<String> texts;

    public SetStreetSignTextPacket(BlockPos pos, List<String> texts) {
        this.pos = pos;
        this.texts = texts;
    }

    public static void encode(SetStreetSignTextPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.texts.size());
        for (String text : msg.texts) {
            buf.writeUtf(text);
        }
    }

    public static SetStreetSignTextPacket decode(FriendlyByteBuf buf) {
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
}
