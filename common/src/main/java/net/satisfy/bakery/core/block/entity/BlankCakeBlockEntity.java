package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.recipe.CakeAnimation;
import net.satisfy.bakery.core.recipe.Filling;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlankCakeBlockEntity extends BlockEntity {
    public static final int MAX_SHELLS = 4;
    private CakeAnimation animation = CakeAnimation.NONE;
    private long animationStart;
    private int animationDuration;
    private int presses;
    private int pressesNeeded;
    private int pressesBase;
    private boolean perfectBonus;
    @Nullable
    private Filling filling;
    private final NonNullList<ItemStack> shells = NonNullList.withSize(MAX_SHELLS, ItemStack.EMPTY);
    private final List<ItemStack> output = new ArrayList<>();
    private long fillTime = -100;
    @Nullable
    private BlockState pendingState;
    @Nullable
    private UUID worker;

    public BlankCakeBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.BLANK_CAKE_BLOCK_ENTITY.get(), pos, state);
    }

    public CakeAnimation getAnimation() {
        return animation;
    }

    public long getAnimationStart() {
        return animationStart;
    }

    public int getAnimationDuration() {
        return Math.max(1, animationDuration);
    }

    public float getProgress(long gameTime, float partialTick) {
        return Math.min(1.0F, Math.max(0.0F, (gameTime - animationStart + partialTick) / getAnimationDuration()));
    }

    public int getPresses() {
        return presses;
    }

    public int getPressesNeeded() {
        return pressesNeeded;
    }

    public boolean canPress(long gameTime) {
        return animation.isPressing() && presses < pressesNeeded && !isTooFast(gameTime);
    }

    public boolean isTooFast(long gameTime) {
        return presses > 0 && gameTime - animationStart < CakeAnimation.PRESS_DURATION;
    }

    public void squish(long gameTime) {
        if (pressesNeeded < pressesBase * 2) {
            pressesNeeded++;
        }
        animationStart = gameTime;
        sync();
    }

    public boolean isPressedFlat() {
        return animation.isPressing() && presses >= pressesNeeded;
    }

    public void press(long gameTime) {
        presses++;
        animationStart = gameTime;
        sync();
    }

    public float pressProgress(long gameTime, float partialTick) {
        float needed = Math.max(1, pressesNeeded);
        float before = Math.max(0, presses - 1) / needed;
        float after = presses / needed;
        return Mth.lerp(CakeAnimation.easeOutCubic(getProgress(gameTime, partialTick)), before, after);
    }

    public @Nullable Filling getFilling() {
        return filling;
    }

    public void setFilling(@Nullable Filling filling) {
        this.filling = filling;
        sync();
    }

    public NonNullList<ItemStack> getShells() {
        return shells;
    }

    public boolean addShell(ItemStack shell) {
        for (int slot = 0; slot < MAX_SHELLS; slot++) {
            if (shells.get(slot).isEmpty()) {
                shells.set(slot, shell.copyWithCount(1));
                sync();
                return true;
            }
        }
        return false;
    }

    public int fillShells(ItemStack filled) {
        int count = 0;
        for (int slot = 0; slot < MAX_SHELLS; slot++) {
            if (shells.get(slot).is(ObjectRegistry.CORNET_SHELL.get())) {
                shells.set(slot, filled.copyWithCount(1));
                count++;
            }
        }
        if (count > 0) {
            fillTime = level != null ? level.getGameTime() : 0;
            sync();
        }
        return count;
    }

    public long getFillTime() {
        return fillTime;
    }

    public List<ItemStack> takeShells() {
        List<ItemStack> taken = shells.stream().filter(stack -> !stack.isEmpty()).map(ItemStack::copy).toList();
        for (int slot = 0; slot < MAX_SHELLS; slot++) {
            shells.set(slot, ItemStack.EMPTY);
        }
        sync();
        return taken;
    }

    public void setOutput(List<ItemStack> stacks) {
        output.clear();
        output.addAll(stacks);
        setChanged();
    }

    public List<ItemStack> takeOutput() {
        List<ItemStack> taken = List.copyOf(output);
        output.clear();
        return taken;
    }

    public float heightScale(long gameTime, float partialTick) {
        float progress = getProgress(gameTime, partialTick);
        if (animation == CakeAnimation.ROLL_UP) {
            return 1.0F;
        }
        if (!animation.isPressing()) {
            return animation.verticalScale(progress, getAnimationDuration());
        }
        float needed = Math.max(1, pressesNeeded);
        float before = Math.max(0, presses - 1) / needed;
        float after = presses / needed;
        float pressed = Mth.lerp(CakeAnimation.easeOutCubic(progress), before, after);
        float squash = presses > 0 ? Mth.sin(progress * Mth.PI) * 0.08F : 0.0F;
        return Math.max(Mth.lerp(pressed, 1.0F, 1.0F / 6.0F) * (1.0F - squash), 0.02F);
    }

    public void setPerfectBonus(boolean perfectBonus) {
        this.perfectBonus = perfectBonus;
        setChanged();
    }

    public boolean hasPerfectBonus() {
        return perfectBonus;
    }

    public @Nullable BlockState getPendingState() {
        return pendingState;
    }

    public @Nullable UUID getWorker() {
        return worker;
    }

    public void start(CakeAnimation newAnimation, long gameTime, BlockState result) {
        start(newAnimation, gameTime, result, null);
    }

    public void start(CakeAnimation newAnimation, long gameTime, BlockState result, @Nullable UUID newWorker) {
        animation = newAnimation;
        animationDuration = newAnimation.configuredDuration();
        presses = 0;
        pressesNeeded = newAnimation.isPressing() ? newAnimation.configuredPresses() : 0;
        pressesBase = pressesNeeded;
        perfectBonus = false;
        worker = newWorker;
        animationStart = gameTime;
        pendingState = result;
        sync();
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
        animation = CakeAnimation.byName(tag.getString("Animation"));
        animationStart = tag.getLong("AnimationStart");
        animationDuration = tag.contains("AnimationDuration") ? tag.getInt("AnimationDuration") : animation.duration();
        presses = tag.getInt("Presses");
        pressesNeeded = tag.getInt("PressesNeeded");
        pressesBase = tag.contains("PressesBase") ? tag.getInt("PressesBase") : pressesNeeded;
        perfectBonus = tag.getBoolean("PerfectBonus");
        fillTime = tag.contains("FillTime") ? tag.getLong("FillTime") : -100;
        filling = tag.contains("Filling") ? Filling.CODEC.parse(NbtOps.INSTANCE, tag.get("Filling")).result().orElse(null) : null;
        for (int slot = 0; slot < MAX_SHELLS; slot++) {
            shells.set(slot, ItemStack.EMPTY);
        }
        ListTag shellList = tag.getList("Shells", Tag.TAG_COMPOUND);
        for (int slot = 0; slot < Math.min(MAX_SHELLS, shellList.size()); slot++) {
            shells.set(slot, ItemStack.parseOptional(provider, shellList.getCompound(slot)));
        }
        output.clear();
        for (Tag entry : tag.getList("Output", Tag.TAG_COMPOUND)) {
            ItemStack.parse(provider, entry).ifPresent(output::add);
        }
        worker = tag.hasUUID("Worker") ? tag.getUUID("Worker") : null;
        pendingState = tag.contains("PendingState") ? NbtUtils.readBlockState(provider.lookupOrThrow(Registries.BLOCK), tag.getCompound("PendingState")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putString("Animation", animation.getSerializedName());
        tag.putLong("AnimationStart", animationStart);
        tag.putInt("AnimationDuration", animationDuration);
        tag.putInt("Presses", presses);
        tag.putInt("PressesNeeded", pressesNeeded);
        tag.putInt("PressesBase", pressesBase);
        tag.putBoolean("PerfectBonus", perfectBonus);
        tag.putLong("FillTime", fillTime);
        if (filling != null) {
            Filling.CODEC.encodeStart(NbtOps.INSTANCE, filling).result().ifPresent(encoded -> tag.put("Filling", encoded));
        }
        ListTag shellList = new ListTag();
        shells.forEach(stack -> shellList.add(stack.saveOptional(provider)));
        tag.put("Shells", shellList);
        ListTag outputList = new ListTag();
        output.forEach(stack -> outputList.add(stack.save(provider)));
        tag.put("Output", outputList);
        if (worker != null) {
            tag.putUUID("Worker", worker);
        }
        if (pendingState != null) {
            tag.put("PendingState", NbtUtils.writeBlockState(pendingState));
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
