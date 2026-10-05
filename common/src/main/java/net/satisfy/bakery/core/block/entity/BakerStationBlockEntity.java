package net.satisfy.bakery.core.block.entity;

import net.satisfy.farm_and_charm.core.util.StoredExperience;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;

public class BakerStationBlockEntity extends BlockEntity {
    public static final int TOOL_SLOTS = 2;

    private final NonNullList<ItemStack> tools = NonNullList.withSize(TOOL_SLOTS, ItemStack.EMPTY);
    private final StoredExperience experience = new StoredExperience();

    public BakerStationBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.BAKER_STATION_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getTool(int slot) {
        return tools.get(slot);
    }

    public NonNullList<ItemStack> getTools() {
        return tools;
    }

    public boolean addTool(int slot, ItemStack stack) {
        if (!tools.get(slot).isEmpty()) {
            return false;
        }
        tools.set(slot, stack.split(1));
        sync();
        return true;
    }

    public ItemStack takeTool(int slot) {
        ItemStack tool = tools.get(slot);
        tools.set(slot, ItemStack.EMPTY);
        sync();
        return tool;
    }

    public void awardExperience(ServerLevel level, float amount, Vec3 pos) {
        experience.add(amount);
        experience.award(level, pos);
        setChanged();
    }

    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        for (int slot = 0; slot < TOOL_SLOTS; slot++) {
            tools.set(slot, ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(tag, tools, provider);
        experience.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ContainerHelper.saveAllItems(tag, tools, true, provider);
        experience.save(tag);
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
