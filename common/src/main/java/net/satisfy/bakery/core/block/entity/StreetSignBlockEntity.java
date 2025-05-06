package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class StreetSignBlockEntity extends BlockEntity {
    private final Component[] text = new Component[]{Component.literal(""), Component.literal(""), Component.literal("")};
    private boolean glowing = false;

    public StreetSignBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.STREET_SIGN_BLOCK_ENTITY.get(), pos, state);
    }

    public Component getText(int line) {
        return text[line];
    }

    public void setText(int line, Component component) {
        this.text[line] = component;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        glowing = tag.getBoolean("Glowing");
        for (int i = 0; i < 3; i++) {
            if (tag.contains("Text" + i)) {
                text[i] = Component.Serializer.fromJson(tag.getString("Text" + i));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Glowing", glowing);
        for (int i = 0; i < 3; i++) {
            tag.putString("Text" + i, Component.Serializer.toJson(text[i]));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
