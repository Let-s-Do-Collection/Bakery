package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.SmallCookingPotBlock;
import net.satisfy.bakery.core.recipe.JamConsistency;
import net.satisfy.bakery.core.recipe.JamRecipe;
import net.satisfy.bakery.core.registry.DataComponentRegistry;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import net.satisfy.farm_and_charm.core.registry.SoundEventRegistry;
import net.satisfy.farm_and_charm.core.registry.TagRegistry;
import net.satisfy.foundation.registry.FoundationParticles;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmallCookingPotBlockEntity extends BlockEntity {
    public static final int MAX_INGREDIENTS = 4;
    public static final int STIR_INTERVAL = 160;
    public static final int STIR_WINDOW = 80;
    public static final int PERFECT_WINDOW = 200;
    public static final float WHISK_MAX_SPEED = 1.2F;

    public static final float RUNNY_FROM = 0.6F;
    private static final int MISSED_STIRS_FOR_CARAMEL = 2;

    public static final int CARAMEL_WINDOW = 300;

    private final List<ItemStack> ingredients = new ArrayList<>();
    @Nullable
    private ResourceLocation recipeId;
    private ItemStack result = ItemStack.EMPTY;
    private int color = 0xFFFFFF;
    private int cookingTime;
    private int timer;
    private boolean stirDue;
    private int stirDeadline;
    private int missedStirs;
    private long lastStir = -100;
    private float whiskSpeed;
    private long lastDump = -100;
    private float whiskAngle;
    private float previousWhiskAngle;
    @Nullable
    private JamConsistency consistency;
    private int jarsLeft;
    private boolean burnt;
    private String cook = "";

    public SmallCookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.SMALL_COOKING_POT_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SmallCookingPotBlockEntity pot) {
        boolean heated = level.getBlockState(pos.below()).is(TagRegistry.ALLOWS_COOKING);
        if (state.getValue(SmallCookingPotBlock.LIT) != heated) {
            level.setBlock(pos, state.setValue(SmallCookingPotBlock.LIT, heated), Block.UPDATE_ALL);
        }
        if (heated && pot.isCooking()) {
            pot.stepCooking(level);
        }
        pot.spinWhisk(level);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SmallCookingPotBlockEntity pot) {
        pot.previousWhiskAngle = pot.whiskAngle;
        pot.whiskAngle += pot.whiskSpeed;
        pot.whiskSpeed = pot.decay(pot.whiskSpeed);
    }

    private float decay(float speed) {
        float next = speed * Mth.lerp(getViscosity(), 0.96F, 0.7F);
        return next < 0.005F ? 0.0F : next;
    }

    public float getViscosity() {
        if (burnt) {
            return 1.0F;
        }
        if (consistency != null) {
            return switch (consistency) {
                case RUNNY -> 0.1F;
                case PERFECT -> 0.4F;
                case CARAMELIZED -> 0.8F;
            };
        }
        if (recipeId == null || cookingTime <= 0) {
            return 0.0F;
        }
        if (timer <= cookingTime) {
            return 0.4F * timer / cookingTime;
        }
        return Math.min(0.9F, 0.4F + 0.5F * (timer - cookingTime) / (PERFECT_WINDOW + CARAMEL_WINDOW));
    }

    private void spinWhisk(Level level) {
        if (whiskSpeed <= 0.0F) {
            return;
        }
        if (level instanceof ServerLevel serverLevel && (isCooking() || isBottling()) && whiskSpeed > 0.3F && level.getGameTime() % 4 == 0) {
            int amount = Math.round(whiskSpeed * 2.0F);
            double x = worldPosition.getX() + 0.5, y = worldPosition.getY() + 0.3, z = worldPosition.getZ() + 0.5;
            serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.DYE_SPLASH.get(), 0xFF000000 | getDisplayColor()), x, y, z, amount, 0.12, 0.02, 0.12, 0.05 + whiskSpeed * 0.1);
            serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.COLORED_SOUP_BUBBLE.get(), 0xFF000000 | getDisplayColor()), x, y, z, 1, 0.12, 0.0, 0.12, 0.0);
        }
        whiskSpeed = decay(whiskSpeed);
    }

    private void stepCooking(Level level) {
        timer++;
        if (stirDue && timer > stirDeadline) {
            stirDue = false;
            missedStirs++;
            level.playSound(null, worldPosition, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.4F, 0.7F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE, worldPosition.getX() + 0.5, worldPosition.getY() + 0.45, worldPosition.getZ() + 0.5, 6, 0.1, 0.05, 0.1, 0.01);
            }
            sync();
        }
        if (!stirDue && timer < cookingTime && timer % STIR_INTERVAL == 0) {
            stirDue = true;
            stirDeadline = timer + STIR_WINDOW;
            level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.BLOCKS, 0.9F, 0.8F);
            sync();
        }
        if (timer > cookingTime + PERFECT_WINDOW + CARAMEL_WINDOW) {
            burnt = true;
            stirDue = false;
            level.playSound(null, worldPosition, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 0.5F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, worldPosition.getX() + 0.5, worldPosition.getY() + 0.45, worldPosition.getZ() + 0.5, 10, 0.12, 0.05, 0.12, 0.02);
            }
            sync();
            return;
        }
        if (timer == cookingTime) {
            level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5F, 1.2F);
            sync();
        } else if (timer % 20 == 0) {
            sync();
        }
    }

    public boolean addIngredient(ItemStack stack, Player player) {
        if (level == null || recipeId != null || consistency != null || ingredients.size() >= MAX_INGREDIENTS) {
            return false;
        }
        List<ItemStack> candidate = new ArrayList<>(ingredients);
        candidate.add(stack.copyWithCount(1));
        if (recipes().stream().noneMatch(recipe -> recipe.value().accepts(candidate))) {
            return false;
        }
        ingredients.add(stack.copyWithCount(1));
        ItemStack remainder = stack.getItem().hasCraftingRemainingItem() ? new ItemStack(stack.getItem().getCraftingRemainingItem()) : ItemStack.EMPTY;
        stack.consume(1, player);
        if (!remainder.isEmpty() && !player.getAbilities().instabuild && !player.getInventory().add(remainder)) {
            player.drop(remainder, false);
        }
        level.playSound(null, worldPosition, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 0.5F, 1.3F + level.random.nextFloat() * 0.2F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack.copyWithCount(1)), worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5, 4, 0.08, 0.02, 0.08, 0.04);
        }

        JamRecipe.Input input = new JamRecipe.Input(List.copyOf(ingredients));
        level.getRecipeManager().getRecipeFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get(), input, level).ifPresent(holder -> {
            recipeId = holder.id();
            result = holder.value().getResult().copy();
            color = holder.value().getColor();
            cookingTime = holder.value().getCookingTime();
            timer = 0;
            missedStirs = 0;
            stirDue = false;
            cook = player.getGameProfile().getName();
        });
        sync();
        return true;
    }

    public boolean dump(@Nullable Player player) {
        if (level == null || isEmpty()) {
            return false;
        }
        if (player != null && recipeId == null && consistency == null) {
            for (ItemStack ingredient : ingredients) {
                if (!player.getInventory().add(ingredient.copy())) {
                    player.drop(ingredient.copy(), false);
                }
            }
        } else if (level instanceof ServerLevel serverLevel) {
            Direction side = getBlockState().getValue(SmallCookingPotBlock.FACING).getClockWise();
            double x = worldPosition.getX() + 0.5 + side.getStepX() * 0.35, y = worldPosition.getY() + 0.3, z = worldPosition.getZ() + 0.5 + side.getStepZ() * 0.35;
            serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.DYE_SPLASH.get(), 0xFF000000 | getDisplayColor()), x, y, z, 4, 0.08, 0.05, 0.08, 0.08);
            if (burnt) {
                serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 8, 0.1, 0.05, 0.1, 0.02);
            }
        }
        reset();
        lastDump = level.getGameTime();
        level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.6F, 0.8F);
        sync();
        return true;
    }

    public boolean isEmpty() {
        return ingredients.isEmpty() && recipeId == null && consistency == null && !burnt;
    }

    public long getLastDump() {
        return lastDump;
    }

    public boolean stir() {
        if (level == null) {
            return false;
        }
        float viscosity = getViscosity();
        float limit = burnt ? 0.15F : Mth.lerp(viscosity, WHISK_MAX_SPEED, 0.3F);
        float impulse = burnt ? 0.15F : Mth.lerp(viscosity, 0.18F, 0.05F);
        whiskSpeed = Math.min(limit, whiskSpeed + impulse);
        if (isCooking()) {
            lastStir = level.getGameTime();
            stirDue = false;
        }
        if ((isCooking() || isBottling()) && level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.DYE_SPLASH.get(), 0xFF000000 | getDisplayColor()),
                    worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5, 1, 0.12, 0.02, 0.12, 0.06);
        }
        level.playSound(null, worldPosition, burnt ? SoundEvents.MUD_STEP : SoundEventRegistry.CRAFTING_BOWL_STIRRING.get(), SoundSource.BLOCKS,
                0.5F + whiskSpeed * 0.3F, Math.max(0.5F, 0.9F + whiskSpeed * 0.8F - viscosity * 0.4F));
        sync();
        return true;
    }

    public boolean canBottle() {
        return consistency != null ? jarsLeft > 0 : isCooking() && timer >= cookingTime * RUNNY_FROM;
    }

    public ItemStack bottle() {
        if (level == null || !canBottle()) {
            return ItemStack.EMPTY;
        }
        if (consistency == null) {
            consistency = currentConsistency();
            int bonus = switch (consistency) {
                case RUNNY -> 1;
                case PERFECT -> 0;
                case CARAMELIZED -> -1;
            };
            jarsLeft = Math.max(1, result.getCount() + bonus);
            ingredients.clear();
            stirDue = false;
        }
        ItemStack jam = jarOf(1);
        if (level instanceof ServerLevel serverLevel) {
            int color = 0xFF000000 | getDisplayColor();
            double x = worldPosition.getX() + 0.5, y = worldPosition.getY() + 0.35, z = worldPosition.getZ() + 0.5;
            serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.DYE_SPLASH.get(), color), x, y, z, 2, 0.1, 0.02, 0.1, 0.06);
            if (consistency == JamConsistency.PERFECT) {
                serverLevel.sendParticles(ColorParticleOption.create(FoundationParticles.COLORED_SOUP_BUBBLE.get(), color), x, y, z, 3, 0.12, 0.02, 0.12, 0.0);
                level.playSound(null, worldPosition, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 0.8F, 1.4F);
            }
        }
        jarsLeft--;
        level.playSound(null, worldPosition, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 0.8F, 0.9F);
        if (jarsLeft <= 0) {
            reset();
        }
        sync();
        return jam;
    }

    private ItemStack jarOf(int count) {
        ItemStack jam = result.copyWithCount(count);
        if (consistency == JamConsistency.PERFECT) {
            jam.set(DataComponentRegistry.PERFECT_JAM.get(), cook);
            jam.set(DataComponents.ITEM_NAME, Component.translatable("item.bakery.perfect_jam", result.getHoverName()));
        }
        return jam;
    }

    public JamConsistency currentConsistency() {
        if (consistency != null) {
            return consistency;
        }
        if (timer < cookingTime) {
            return JamConsistency.RUNNY;
        }
        if (timer > cookingTime + PERFECT_WINDOW || missedStirs >= MISSED_STIRS_FOR_CARAMEL) {
            return JamConsistency.CARAMELIZED;
        }
        return JamConsistency.PERFECT;
    }

    private void reset() {
        ingredients.clear();
        recipeId = null;
        result = ItemStack.EMPTY;
        color = 0xFFFFFF;
        cookingTime = 0;
        timer = 0;
        stirDue = false;
        missedStirs = 0;
        consistency = null;
        jarsLeft = 0;
        burnt = false;
        cook = "";
    }

    public List<ItemStack> getNextIngredients() {
        List<ItemStack> next = new ArrayList<>();
        if (level == null || recipeId != null || consistency != null) {
            return next;
        }
        for (RecipeHolder<JamRecipe> holder : recipes()) {
            JamRecipe recipe = holder.value();
            if (!recipe.accepts(ingredients)) {
                continue;
            }
            recipe.getIngredients().forEach(ingredient -> {
                ItemStack[] choices = ingredient.getItems();
                if (choices.length == 0) {
                    return;
                }
                List<ItemStack> candidate = new ArrayList<>(ingredients);
                candidate.add(choices[0]);
                if (recipe.accepts(candidate) && next.stream().noneMatch(known -> ItemStack.isSameItem(known, choices[0]))) {
                    next.add(choices[0]);
                }
            });
        }
        return next;
    }

    private List<RecipeHolder<JamRecipe>> recipes() {
        return level == null ? List.of() : level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get());
    }

    public List<ItemStack> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>(ingredients);
        if (consistency != null && jarsLeft > 0) {
            drops.add(jarOf(jarsLeft));
        }
        return drops;
    }

    public boolean isCooking() {
        return recipeId != null && consistency == null && !burnt;
    }

    public boolean isBurnt() {
        return burnt;
    }

    public boolean isBottling() {
        return consistency != null && jarsLeft > 0;
    }

    public float getStirUrgency() {
        return stirDue ? Mth.clamp(1.0F - (stirDeadline - timer) / (float) STIR_WINDOW, 0.0F, 1.0F) : 0.0F;
    }

    public boolean isStirDue() {
        return stirDue;
    }

    public boolean isReady() {
        return isCooking() && timer >= cookingTime;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getDisplayColor() {
        if (burnt) {
            return 0x1A1210;
        }
        float progress = cookingTime <= 0 ? 1.0F : Mth.clamp(timer / (float) cookingTime, 0.0F, 1.0F);
        float r = (color >> 16 & 255) / 255.0F, g = (color >> 8 & 255) / 255.0F, b = (color & 255) / 255.0F;
        float lighten = 0.3F * (1.0F - progress);
        r = Mth.lerp(lighten, r, 1.0F);
        g = Mth.lerp(lighten, g, 1.0F);
        b = Mth.lerp(lighten, b, 1.0F);
        if (isCooking() && timer > cookingTime) {
            float caramel = Mth.clamp((timer - cookingTime) / (float) (PERFECT_WINDOW + CARAMEL_WINDOW), 0.0F, 1.0F) * 0.75F;
            r = Mth.lerp(caramel, r, 0.42F);
            g = Mth.lerp(caramel, g, 0.22F);
            b = Mth.lerp(caramel, b, 0.1F);
        }
        return (int) (r * 255) << 16 | (int) (g * 255) << 8 | (int) (b * 255);
    }

    public int getColor() {
        return color;
    }

    public int getTimer() {
        return timer;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public float getWhiskSpeed() {
        return whiskSpeed;
    }

    public float getWhiskAngle(float partialTick) {
        return Mth.lerp(partialTick, previousWhiskAngle, whiskAngle);
    }

    public long getLastStir() {
        return lastStir;
    }

    public int getJarsLeft() {
        return jarsLeft;
    }

    public @Nullable JamConsistency getConsistency() {
        return consistency;
    }

    public static boolean isJar(ItemStack stack) {
        return stack.is(ObjectRegistry.JAR.get().asItem());
    }

    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        ingredients.clear();
        for (Tag entry : tag.getList("Ingredients", Tag.TAG_COMPOUND)) {
            ItemStack.parse(provider, entry).ifPresent(ingredients::add);
        }
        recipeId = tag.contains("Recipe") ? ResourceLocation.tryParse(tag.getString("Recipe")) : null;
        result = tag.contains("Result") ? ItemStack.parseOptional(provider, tag.getCompound("Result")) : ItemStack.EMPTY;
        color = tag.contains("Color") ? tag.getInt("Color") : 0xFFFFFF;
        cookingTime = tag.getInt("CookingTime");
        timer = tag.getInt("Timer");
        stirDue = tag.getBoolean("StirDue");
        stirDeadline = tag.getInt("StirDeadline");
        missedStirs = tag.getInt("MissedStirs");
        lastStir = tag.contains("LastStir") ? tag.getLong("LastStir") : -100;
        whiskSpeed = tag.getFloat("WhiskSpeed");
        lastDump = tag.contains("LastDump") ? tag.getLong("LastDump") : -100;
        consistency = tag.contains("Consistency") ? JamConsistencyLookup.byName(tag.getString("Consistency")) : null;
        jarsLeft = tag.getInt("JarsLeft");
        burnt = tag.getBoolean("Burnt");
        cook = tag.getString("Cook");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ListTag list = new ListTag();
        ingredients.forEach(stack -> list.add(stack.save(provider)));
        tag.put("Ingredients", list);
        if (recipeId != null) {
            tag.putString("Recipe", recipeId.toString());
        }
        if (!result.isEmpty()) {
            tag.put("Result", result.save(provider));
        }
        tag.putInt("Color", color);
        tag.putInt("CookingTime", cookingTime);
        tag.putInt("Timer", timer);
        tag.putBoolean("StirDue", stirDue);
        tag.putInt("StirDeadline", stirDeadline);
        tag.putInt("MissedStirs", missedStirs);
        tag.putLong("LastStir", lastStir);
        tag.putFloat("WhiskSpeed", whiskSpeed);
        tag.putLong("LastDump", lastDump);
        if (consistency != null) {
            tag.putString("Consistency", consistency.getSerializedName());
        }
        tag.putInt("JarsLeft", jarsLeft);
        tag.putBoolean("Burnt", burnt);
        tag.putString("Cook", cook);
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

    private static final class JamConsistencyLookup {
        private static @Nullable JamConsistency byName(String name) {
            for (JamConsistency value : JamConsistency.values()) {
                if (value.getSerializedName().equals(name)) {
                    return value;
                }
            }
            return null;
        }
    }
}
