package net.satisfy.bakery.core.block.entity;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
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
import net.satisfy.farm_and_charm.core.registry.RecipeTypeRegistry;
import net.satisfy.farm_and_charm.core.registry.TagRegistry;
import net.satisfy.farm_and_charm.core.world.ImplementedInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static net.minecraft.world.item.ItemStack.isSameItemSameComponents;

public class SmallCookingPotBlockEntity extends BlockEntity implements BlockEntityTicker<SmallCookingPotBlockEntity>, ImplementedInventory, MenuProvider {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(MAX_CAPACITY, ItemStack.EMPTY);
    private static final int MAX_CAPACITY = 8, CONTAINER_SLOT = 6, OUTPUT_SLOT = 7, INGREDIENTS_AREA = 2 * 3;
    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6};
    private int cookingTime;
    private boolean isBeingBurned;
    private static final int MAX_COOKING_TIME = 300;

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

    public int @NotNull [] getSlotsForFace(Direction side) {
        return switch (side) {
            case UP -> SLOTS_FOR_UP;
            case DOWN -> new int[]{OUTPUT_SLOT};
            default -> new int[]{CONTAINER_SLOT};
        };
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        ContainerHelper.loadAllItems(compoundTag, inventory, provider);
        cookingTime = compoundTag.getInt("CookingTime");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, inventory, provider);
        compoundTag.putInt("CookingTime", cookingTime);
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
            ItemStack recipeOutput = generateOutputItem(recipe, access);
            if (!outputSlotStack.isEmpty() && (!isSameItemSameComponents(outputSlotStack, recipeOutput) || outputSlotStack.getCount() >= outputSlotStack.getMaxStackSize()))
                return false;
            NonNullList<ItemStack> temp = NonNullList.create();
            for (int i = 0; i < INGREDIENTS_AREA; i++) {
                temp.add(getItem(i).copy());
            }
            for (var ingredient : cookingRecipe.getIngredients()) {
                boolean found = false;
                for (ItemStack stack : temp) {
                    if (ingredient.test(stack) && !stack.isEmpty()) {
                        stack.shrink(1);
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
            return true;
        }
        return false;
    }

    private void craft(Recipe<?> recipe, RegistryAccess access) {
        if (!canCraft(recipe, access)) return;
        ItemStack recipeOutput = generateOutputItem(recipe, access), outputSlotStack = getItem(OUTPUT_SLOT);
        if (outputSlotStack.isEmpty()) {
            setItem(OUTPUT_SLOT, recipeOutput);
        } else {
            outputSlotStack.grow(recipeOutput.getCount());
        }
        recipe.getIngredients().forEach(ingredient -> {
            for (int slot = 0; slot < INGREDIENTS_AREA; slot++) {
                ItemStack stack = getItem(slot);
                if (ingredient.test(stack)) {
                    ItemStack remainderStack = stack.getItem().hasCraftingRemainingItem() ? new ItemStack(Objects.requireNonNull(stack.getItem().getCraftingRemainingItem())) : ItemStack.EMPTY;
                    stack.shrink(1);
                    if (!remainderStack.isEmpty()) setItem(slot, remainderStack);
                    break;
                }
            }
        });
        ItemStack containerSlotStack = getItem(CONTAINER_SLOT);
        if (!containerSlotStack.isEmpty()) {
            containerSlotStack.shrink(1);
            if (containerSlotStack.isEmpty()) setItem(CONTAINER_SLOT, ItemStack.EMPTY);
        }
    }

    private ItemStack generateOutputItem(Recipe<?> recipe, RegistryAccess access) {
        ItemStack outputStack = recipe.getResultItem(access).copy();
        outputStack.setCount(1);
        if (outputStack.getItem() instanceof EffectFood) {
            recipe.getIngredients().forEach(ingredient -> {
                for (int slot = 0; slot < INGREDIENTS_AREA; slot++) {
                    ItemStack stack = getItem(slot);
                    if (ingredient.test(stack)) {
                        EffectFoodHelper.getEffects(stack).forEach(effect -> EffectFoodHelper.addEffect(outputStack, effect));
                        break;
                    }
                }
            });
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

        List<CookingPotRecipe> recipes = null;
        List<RecipeHolder<CookingPotRecipe>> recipeHolders = world.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.COOKING_POT_RECIPE_TYPE.get());
        recipeHolders.forEach(recipe -> {
            if (level == null) throw new IllegalStateException("Null world not allowed");
            RegistryAccess access = level.registryAccess();
            if (recipe != null) {
                if (canCraft(recipe.value(), access)) {
                    if (++cookingTime >= MAX_COOKING_TIME) {
                        cookingTime = 0;
                        craft(recipe.value(), access);
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
        });
    }


    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    public boolean stillValid(Player player) {
        if (level == null || level.getBlockEntity(worldPosition) != this) return false;
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public @NotNull Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new CookingPotGuiHandler(syncId, inv, this, delegate);
    }
}
