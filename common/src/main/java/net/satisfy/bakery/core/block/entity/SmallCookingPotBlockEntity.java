package net.satisfy.bakery.core.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.SmallCookingPotBlock;
import net.satisfy.bakery.core.registry.EntityTypeRegistry;
import net.satisfy.farm_and_charm.client.gui.handler.CookingPotGuiHandler;
import net.satisfy.farm_and_charm.core.item.food.EffectFood;
import net.satisfy.farm_and_charm.core.item.food.EffectFoodHelper;
import net.satisfy.farm_and_charm.core.recipe.CookingPotRecipe;
import net.satisfy.farm_and_charm.core.recipe.RecipeUnlockManager;
import net.satisfy.farm_and_charm.core.registry.RecipeTypeRegistry;
import net.satisfy.farm_and_charm.core.registry.TagRegistry;
import net.satisfy.farm_and_charm.core.world.ImplementedInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SmallCookingPotBlockEntity extends BlockEntity implements BlockEntityTicker<SmallCookingPotBlockEntity>, ImplementedInventory, MenuProvider, Container {
    private static final int FIRST_INGREDIENT_SLOT = 0;
    private static final int LAST_INGREDIENT_SLOT = 5;
    private static final int CONTAINER_SLOT = 6;
    private static final int OUTPUT_SLOT = 7;
    private static final int MAX_CAPACITY = 8;
    private static final int MAX_COOKING_TIME = 900;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(MAX_CAPACITY, ItemStack.EMPTY);
    private int cookingTime;
    private boolean isBeingBurned;
    private UUID ownerUuid;

    private final ContainerData delegate = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> cookingTime;
                case 1 -> isBeingBurned ? 1 : 0;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> cookingTime = value;
                case 1 -> isBeingBurned = value != 0;
            }
        }

        public int getCount() {
            return 2;
        }
    };

    public SmallCookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.SMALL_COOKING_POT_BLOCK_ENTITY.get(), pos, state);
    }

    public static int getMaxCookingTime() {
        return MAX_COOKING_TIME;
    }

    public boolean hasOutputItem() {
        return !getItem(OUTPUT_SLOT).isEmpty();
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        return switch (side) {
            case UP -> new int[]{0, 1, 2, 3, 4, 5};
            case DOWN -> new int[]{OUTPUT_SLOT};
            default -> new int[]{CONTAINER_SLOT};
        };
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        NonNullList<ItemStack> loaded = NonNullList.withSize(MAX_CAPACITY, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, loaded, provider);
        for (int index = 0; index < MAX_CAPACITY; index++) {
            inventory.set(index, loaded.get(index));
        }
        cookingTime = tag.getInt("CookingTime");
        if (tag.hasUUID("OwnerUUID")) {
            ownerUuid = tag.getUUID("OwnerUUID");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        ContainerHelper.saveAllItems(tag, inventory, provider);
        tag.putInt("CookingTime", cookingTime);
        if (ownerUuid != null) {
            tag.putUUID("OwnerUUID", ownerUuid);
        }
    }

    public boolean isBeingBurned() {
        if (level == null) throw new IllegalStateException("Null world not allowed");
        BlockState belowState = level.getBlockState(worldPosition.below());
        return belowState.is(TagRegistry.ALLOWS_COOKING);
    }

    private boolean canCraft(Recipe<?> recipe, RegistryAccess access) {
        if (recipe == null || recipe.getResultItem(access).isEmpty()) return false;
        if (recipe instanceof CookingPotRecipe cookingRecipe) {
            if (cookingRecipe.isContainerRequired()) {
                ItemStack containerSlotStack = getItem(CONTAINER_SLOT);
                if (!containerSlotStack.is(cookingRecipe.getContainerItem().getItem())) return false;
            }
            ItemStack outputSlotStack = getItem(OUTPUT_SLOT);
            ItemStack expected = generateOutputItem(recipe, access);
            return outputSlotStack.isEmpty() || ItemStack.isSameItemSameComponents(outputSlotStack, expected) && outputSlotStack.getCount() < outputSlotStack.getMaxStackSize();
        }
        return false;
    }

    private void craft(Recipe<?> recipe, RegistryAccess access) {
        if (!canCraft(recipe, access)) return;

        ItemStack recipeOutput = generateOutputItem(recipe, access);
        ItemStack outputSlotStack = getItem(OUTPUT_SLOT);

        if (outputSlotStack.isEmpty()) {
            setItem(OUTPUT_SLOT, recipeOutput);
        } else {
            outputSlotStack.grow(recipeOutput.getCount());
        }

        recipe.getIngredients().forEach(ingredient -> {
            for (int slot = FIRST_INGREDIENT_SLOT; slot <= LAST_INGREDIENT_SLOT; slot++) {
                ItemStack stack = getItem(slot);
                if (stack.isEmpty()) continue;
                if (!ingredient.test(stack)) continue;

                ItemStack remainderStack = stack.getItem().hasCraftingRemainingItem() ? new ItemStack(Objects.requireNonNull(stack.getItem().getCraftingRemainingItem())) : ItemStack.EMPTY;
                stack.shrink(1);

                if (!remainderStack.isEmpty()) {
                    if (stack.isEmpty()) {
                        setItem(slot, remainderStack);
                    } else {
                        boolean inserted = tryInsertRemainder(remainderStack);
                        if (!inserted) {
                            if (level != null) {
                                Block.popResource(level, worldPosition, remainderStack);
                            }
                        }
                    }
                }
                break;
            }
        });

        ItemStack containerSlotStack = getItem(CONTAINER_SLOT);
        if (!containerSlotStack.isEmpty() && containerSlotStack.getItem().hasCraftingRemainingItem()) {
            ItemStack containerRemainder = new ItemStack(Objects.requireNonNull(containerSlotStack.getItem().getCraftingRemainingItem()));
            containerSlotStack.shrink(1);
            if (containerSlotStack.isEmpty()) {
                setItem(CONTAINER_SLOT, containerRemainder);
            } else {
                boolean inserted = tryInsertRemainder(containerRemainder);
                if (!inserted) {
                    if (level != null) {
                        Block.popResource(level, worldPosition, containerRemainder);
                    }
                }
            }
        } else if (!containerSlotStack.isEmpty()) {
            containerSlotStack.shrink(1);
            if (containerSlotStack.isEmpty()) {
                setItem(CONTAINER_SLOT, ItemStack.EMPTY);
            }
        }

        setChanged();
    }

    private boolean tryInsertRemainder(ItemStack remainderStack) {
        if (remainderStack.isEmpty()) return true;

        for (int slot = FIRST_INGREDIENT_SLOT; slot <= LAST_INGREDIENT_SLOT; slot++) {
            ItemStack existingStack = getItem(slot);
            if (existingStack.isEmpty()) {
                setItem(slot, remainderStack);
                return true;
            }
            if (ItemStack.isSameItemSameComponents(existingStack, remainderStack) && existingStack.getCount() < existingStack.getMaxStackSize()) {
                int transferableAmount = Math.min(remainderStack.getCount(), existingStack.getMaxStackSize() - existingStack.getCount());
                if (transferableAmount > 0) {
                    existingStack.grow(transferableAmount);
                    remainderStack.shrink(transferableAmount);
                    if (remainderStack.isEmpty()) {
                        setChanged();
                        return true;
                    }
                }
            }
        }

        ItemStack containerSlotStack = getItem(CONTAINER_SLOT);
        if (containerSlotStack.isEmpty()) {
            setItem(CONTAINER_SLOT, remainderStack);
            return true;
        }
        if (ItemStack.isSameItemSameComponents(containerSlotStack, remainderStack) && containerSlotStack.getCount() < containerSlotStack.getMaxStackSize()) {
            int transferableAmount = Math.min(remainderStack.getCount(), containerSlotStack.getMaxStackSize() - containerSlotStack.getCount());
            if (transferableAmount > 0) {
                containerSlotStack.grow(transferableAmount);
                remainderStack.shrink(transferableAmount);
                setChanged();
                return remainderStack.isEmpty();
            }
        }

        return remainderStack.isEmpty();
    }

    private ItemStack generateOutputItem(Recipe<?> recipe, RegistryAccess access) {
        ItemStack outputStack = recipe.getResultItem(access).copy();
        if (outputStack.getItem() instanceof EffectFood) {
            for (MobEffectInstance inst : EffectFoodHelper.collectMergedSortedEffects(this, FIRST_INGREDIENT_SLOT, LAST_INGREDIENT_SLOT)) {
                EffectFoodHelper.addEffect(outputStack, new Pair<>(inst, 1.0f));
            }
        }
        return outputStack;
    }

    public void tick(Level world, BlockPos pos, BlockState state, SmallCookingPotBlockEntity blockEntity) {
        if (world.isClientSide()) return;

        boolean wasBeingBurned = isBeingBurned;
        isBeingBurned = isBeingBurned();

        if (wasBeingBurned != isBeingBurned || state.getValue(SmallCookingPotBlock.LIT) != isBeingBurned) {
            world.setBlock(pos, state.setValue(SmallCookingPotBlock.LIT, isBeingBurned), Block.UPDATE_ALL);
        }

        if (!isBeingBurned) {
            return;
        }

        RecipeManager recipeManager = world.getRecipeManager();
        List<RecipeHolder<CookingPotRecipe>> recipes = recipeManager.getAllRecipesFor(RecipeTypeRegistry.COOKING_POT_RECIPE_TYPE.get());
        Optional<CookingPotRecipe> recipe = Optional.ofNullable(getRecipe(recipes, inventory));

        if (recipe.isPresent()) {
            CookingPotRecipe cookingRecipe = recipe.get();
            if (cookingRecipe.requiresLearning()) {
                ServerPlayer owner = Objects.requireNonNull(world.getServer()).getPlayerList().getPlayer(ownerUuid);
                if (owner == null || RecipeUnlockManager.isRecipeLocked(owner, BuiltInRegistries.RECIPE_TYPE.getKey(cookingRecipe.getType()))) {
                    cookingTime = 0;
                    if (state.getValue(SmallCookingPotBlock.COOKING)) {
                        world.setBlock(pos, state.setValue(SmallCookingPotBlock.COOKING, false), Block.UPDATE_ALL);
                    }
                    return;
                }
            }
        }

        if (level == null) throw new IllegalStateException("Null world not allowed");
        RegistryAccess access = level.registryAccess();

        if (recipe.isPresent() && canCraft(recipe.get(), access)) {
            if (++cookingTime >= MAX_COOKING_TIME) {
                cookingTime = 0;
                craft(recipe.get(), access);
            }
            if (!state.getValue(SmallCookingPotBlock.COOKING)) {
                world.setBlock(pos, state.setValue(SmallCookingPotBlock.COOKING, true), Block.UPDATE_ALL);
            }
        } else {
            cookingTime = 0;
            if (state.getValue(SmallCookingPotBlock.COOKING)) {
                world.setBlock(pos, state.setValue(SmallCookingPotBlock.COOKING, false), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        setChanged();
    }

    public boolean stillValid(Player player) {
        if (level == null || level.getBlockEntity(worldPosition) != this) return false;
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public @NotNull Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    private CookingPotRecipe getRecipe(List<RecipeHolder<CookingPotRecipe>> recipes, NonNullList<ItemStack> inventory) {
        for (RecipeHolder<CookingPotRecipe> recipeHolder : recipes) {
            CookingPotRecipe recipe = recipeHolder.value();
            if (matchesInventory(recipe, inventory)) {
                return recipe;
            }
        }
        return null;
    }

    private boolean matchesInventory(CookingPotRecipe recipe, NonNullList<ItemStack> inventory) {
        List<Ingredient> ingredients = recipe.getIngredients();
        NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(inventory.size(), ItemStack.EMPTY);

        for (int slot = FIRST_INGREDIENT_SLOT; slot <= LAST_INGREDIENT_SLOT; slot++) {
            inventoryCopy.set(slot, inventory.get(slot).copy());
        }

        for (Ingredient ingredient : ingredients) {
            boolean matched = false;
            for (int slot = FIRST_INGREDIENT_SLOT; slot <= LAST_INGREDIENT_SLOT; slot++) {
                ItemStack ingredientStack = inventoryCopy.get(slot);
                if (!ingredientStack.isEmpty() && ingredient.test(ingredientStack)) {
                    ingredientStack.shrink(1);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }

        outer:
        for (int slot = FIRST_INGREDIENT_SLOT; slot <= LAST_INGREDIENT_SLOT; slot++) {
            ItemStack remaining = inventoryCopy.get(slot);
            if (!remaining.isEmpty()) {
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.test(remaining)) {
                        continue outer;
                    }
                }
                return false;
            }
        }

        if (recipe.isContainerRequired()) {
            ItemStack containerSlotStack = inventory.get(CONTAINER_SLOT);
            return !containerSlotStack.isEmpty() && containerSlotStack.is(recipe.getContainerItem().getItem());
        }

        return true;
    }

    @Nullable
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        ownerUuid = player.getUUID();
        return new CookingPotGuiHandler(syncId, inv, this, delegate);
    }
}