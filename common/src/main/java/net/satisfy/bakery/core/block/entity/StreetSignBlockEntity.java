package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;

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
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        glowing = compoundTag.getBoolean("Glowing");
        for (int i = 0; i < 3; i++) {
            if (compoundTag.contains("Text" + i)) {
                text[i] = Component.Serializer.fromJson(compoundTag.getString("Text" + i), provider);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putBoolean("Glowing", glowing);
        for (int i = 0; i < 3; i++) {
            compoundTag.putString("Text" + i, Component.Serializer.toJson(text[i], provider));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
