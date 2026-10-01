package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.recipe.CakeAnimation;
import net.satisfy.bakery.core.recipe.CakeDecorationRecipe;
import net.satisfy.bakery.core.recipe.CakeLayer;
import net.satisfy.bakery.core.recipe.CakePart;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomCakeBlockEntity extends BlockEntity {
    public static final int MAX_TOPPINGS = 4;
    public static final int MAX_INGREDIENTS = 6;

    @Nullable
    private CakePart filling;
    @Nullable
    private CakePart glaze;
    private final List<CakePart> toppings = new ArrayList<>();
    private final NonNullList<ItemStack> ingredients = NonNullList.withSize(MAX_INGREDIENTS, ItemStack.EMPTY);

    @Nullable
    private ResourceLocation pendingRecipe;
    private int pendingUses;

    private CakeAnimation animation = CakeAnimation.NONE;
    private long animationStart;
    @Nullable
    private CakePart animationPart;

    public CustomCakeBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.CUSTOM_CAKE_BLOCK_ENTITY.get(), pos, state);
    }

    public @Nullable CakePart getFilling() {
        return filling;
    }

    public @Nullable CakePart getGlaze() {
        return glaze;
    }

    public List<CakePart> getToppings() {
        return Collections.unmodifiableList(toppings);
    }

    public List<ItemStack> getIngredients() {
        return ingredients.stream().filter(stack -> !stack.isEmpty()).toList();
    }

    public CakeAnimation getAnimation() {
        return animation;
    }

    public long getAnimationStart() {
        return animationStart;
    }

    public @Nullable CakePart getAnimationPart() {
        return animationPart;
    }

    public boolean isAnimating(long gameTime) {
        return animation != CakeAnimation.NONE && gameTime - animationStart < animation.duration();
    }

    public boolean accepts(CakeLayer layer) {
        return switch (layer) {
            case FILLING -> filling == null && glaze == null && toppings.isEmpty();
            case GLAZE -> glaze == null && toppings.isEmpty();
            case TOPPING -> toppings.size() < MAX_TOPPINGS;
        };
    }

    public int getPendingUses(ResourceLocation recipeId) {
        return recipeId.equals(pendingRecipe) ? pendingUses : 0;
    }

    public void progress(ResourceLocation recipeId, CakeAnimation stepAnimation, CakePart part, long gameTime) {
        if (!recipeId.equals(pendingRecipe)) {
            pendingRecipe = recipeId;
            pendingUses = 0;
        }
        pendingUses++;
        startAnimation(stepAnimation, part, gameTime);
    }

    public void apply(CakeDecorationRecipe recipe, ItemStack ingredient, long gameTime) {
        switch (recipe.layer()) {
            case FILLING -> filling = recipe.part();
            case GLAZE -> glaze = recipe.part();
            case TOPPING -> toppings.add(recipe.part());
        }
        for (int index = 0; index < MAX_INGREDIENTS; index++) {
            if (ingredients.get(index).isEmpty()) {
                ingredients.set(index, ingredient.copyWithCount(1));
                break;
            }
        }
        pendingRecipe = null;
        pendingUses = 0;
        startAnimation(recipe.animation(), recipe.part(), gameTime);
    }

    public void reject(long gameTime) {
        startAnimation(CakeAnimation.WOBBLE, null, gameTime);
    }

    private void startAnimation(CakeAnimation newAnimation, @Nullable CakePart part, long gameTime) {
        animation = newAnimation;
        animationStart = gameTime;
        animationPart = part;
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
        filling = tag.contains("Filling") ? CakePart.load(tag.getCompound("Filling")) : null;
        glaze = tag.contains("Glaze") ? CakePart.load(tag.getCompound("Glaze")) : null;
        toppings.clear();
        for (Tag entry : tag.getList("Toppings", Tag.TAG_COMPOUND)) {
            CakePart topping = CakePart.load((CompoundTag) entry);
            if (topping != null) {
                toppings.add(topping);
            }
        }
        NonNullList<ItemStack> loaded = NonNullList.withSize(MAX_INGREDIENTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, loaded, provider);
        for (int index = 0; index < MAX_INGREDIENTS; index++) {
            ingredients.set(index, loaded.get(index));
        }
        pendingRecipe = tag.contains("PendingRecipe") ? ResourceLocation.tryParse(tag.getString("PendingRecipe")) : null;
        pendingUses = tag.getInt("PendingUses");
        animation = CakeAnimation.byName(tag.getString("Animation"));
        animationStart = tag.getLong("AnimationStart");
        animationPart = tag.contains("AnimationPart") ? CakePart.load(tag.getCompound("AnimationPart")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (filling != null) {
            tag.put("Filling", filling.save());
        }
        if (glaze != null) {
            tag.put("Glaze", glaze.save());
        }
        ListTag toppingList = new ListTag();
        toppings.forEach(topping -> toppingList.add(topping.save()));
        tag.put("Toppings", toppingList);
        ContainerHelper.saveAllItems(tag, ingredients, provider);
        if (pendingRecipe != null) {
            tag.putString("PendingRecipe", pendingRecipe.toString());
            tag.putInt("PendingUses", pendingUses);
        }
        tag.putString("Animation", animation.getSerializedName());
        tag.putLong("AnimationStart", animationStart);
        if (animationPart != null) {
            tag.put("AnimationPart", animationPart.save());
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
