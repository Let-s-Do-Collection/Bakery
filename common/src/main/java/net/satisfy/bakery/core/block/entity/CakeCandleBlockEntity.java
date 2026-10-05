package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.PieBlock;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;

public class CakeCandleBlockEntity extends BlockEntity {
    private Item candle = Items.CANDLE;
    private int pieces = -1;
    private long wobbleStart = Long.MIN_VALUE / 2;

    public CakeCandleBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.CAKE_CANDLE_BLOCK_ENTITY.get(), pos, state);
    }

    public Item getCandle() {
        return candle;
    }

    public Block getCandleBlock() {
        Block block = Block.byItem(candle);
        return block == Blocks.AIR ? Blocks.CANDLE : block;
    }

    public void setCandle(Item candle) {
        this.candle = candle;
        sync();
    }

    public int getPieces() {
        return pieces < 0 ? PieBlock.piecesFromCuts(getBlockState()) : pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
        sync();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockState(BlockState state) {
        BlockState previous = getBlockState();
        super.setBlockState(state);
        if (level != null && level.isClientSide() && previous.hasProperty(PieBlock.CUTS) && state.hasProperty(PieBlock.CUTS)
                && !previous.getValue(PieBlock.CUTS).equals(state.getValue(PieBlock.CUTS))) {
            wobbleStart = level.getGameTime();
        }
    }

    public long getWobbleStart() {
        return wobbleStart;
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
        ResourceLocation id = ResourceLocation.tryParse(tag.getString("Candle"));
        candle = id != null && BuiltInRegistries.ITEM.containsKey(id) ? BuiltInRegistries.ITEM.get(id) : Items.CANDLE;
        pieces = tag.contains("Pieces") ? tag.getInt("Pieces") : -1;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putString("Candle", BuiltInRegistries.ITEM.getKey(candle).toString());
        if (pieces >= 0) {
            tag.putInt("Pieces", pieces);
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
