package net.satisfy.bakery.client.gui.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.bakery.core.block.cake.BlankCakeBlock;
import net.satisfy.bakery.core.block.entity.BakerStationBlockEntity;
import net.satisfy.bakery.core.block.entity.BlankCakeBlockEntity;
import net.satisfy.bakery.core.recipe.BlankCakeInteractionRecipe;
import net.satisfy.bakery.core.recipe.BlankCakeStage;
import net.satisfy.bakery.core.recipe.CakeAnimation;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import net.satisfy.bakery.platform.PlatformHelper;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.List;

public class BakerStationInfoProvider implements BlockInfoProvider {
    private static final ResourceLocation DUNGAREES = ResourceLocation.fromNamespaceAndPath("farm_and_charm", "dungarees");

    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (!PlatformHelper.showBakerStationInfo() || !wearsDungarees()) {
            return List.of();
        }
        if (state.is(ObjectRegistry.BAKER_STATION.get())) {
            return describeStation(level, pos);
        }
        if (state.is(ObjectRegistry.BLANK_CAKE.get())) {
            return describeDough(level, pos, state);
        }
        return List.of();
    }

    private static List<InfoSection> describeStation(Level level, BlockPos pos) {
        if (!level.isEmptyBlock(pos.above())) {
            return List.of();
        }
        if (level.getBlockEntity(pos) instanceof BakerStationBlockEntity station && station.getTools().stream().anyMatch(tool -> !tool.isEmpty())) {
            return List.of();
        }
        return List.of(
                InfoSection.icons(Component.translatable("hud.bakery.station.cake"), List.of(new ItemStack(ObjectRegistry.CAKE_DOUGH.get())), InfoSection.ROW_COLUMNS),
                InfoSection.icons(Component.translatable("hud.bakery.station.pastry"), List.of(new ItemStack(ObjectRegistry.SWEET_DOUGH.get())), InfoSection.ROW_COLUMNS),
                InfoSection.icons(Component.translatable("hud.bakery.station.jam_roll"), List.of(new ItemStack(ObjectRegistry.SPONGE_SHEET.get())), InfoSection.ROW_COLUMNS),
                InfoSection.icons(Component.translatable("hud.bakery.station.cornet"), List.of(new ItemStack(ObjectRegistry.CORNET_SHELL.get())), InfoSection.ROW_COLUMNS)
        );
    }

    private static List<InfoSection> describeDough(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(BlankCakeBlock.ANIMATING)) {
            return describePressing(level, pos);
        }
        BlankCakeStage stage = state.getValue(BlankCakeBlock.STAGE);

        Map<String, List<ItemStack>> actions = new LinkedHashMap<>();
        level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.BLANK_CAKE_INTERACTION_TYPE.get()).stream()
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.matchesStage(stage))
                .sorted(Comparator.comparingInt(BlankCakeInteractionRecipe::priority))
                .forEach(recipe -> {
                    ItemStack[] items = recipe.ingredient().getItems();
                    if (items.length == 0) {
                        return;
                    }
                    List<ItemStack> icons = actions.computeIfAbsent(actionName(recipe.result().animation()), key -> new ArrayList<>());
                    if (icons.stream().noneMatch(known -> known.is(items[0].getItem()))) {
                        icons.add(items[0]);
                    }
                });

        List<InfoSection> sections = new ArrayList<>();
        sections.add(onStation(stage));
        if (level.getBlockEntity(pos) instanceof BlankCakeBlockEntity cake && describeFilling(level, stage, cake, sections)) {
            return sections;
        }
        actions.forEach((action, icons) -> {
            InfoSection section = InfoSection.icons(Component.translatable("hud.bakery.blank_cake." + action), icons, InfoSection.GRID_COLUMNS);
            if (action.equals("roll") && PlatformHelper.isKneadingEnabled()) {
                section = section.withLines(List.of(Component.translatable("hud.bakery.blank_cake.knead_hint").withStyle(ChatFormatting.GRAY)));
            }
            sections.add(section);
        });
        return sections;
    }

    private static List<InfoSection> describePressing(Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof BlankCakeBlockEntity cake) || !cake.getAnimation().isPressing() || cake.isPressedFlat()) {
            return List.of();
        }
        String action = switch (cake.getAnimation()) {
            case KNEAD -> "knead";
            case ROLL_UP -> "roll_up";
            default -> "roll";
        };
        BlankCakeStage stage = cake.getAnimation() == CakeAnimation.ROLL_UP ? BlankCakeStage.SHEET : BlankCakeStage.DOUGH;
        return List.of(onStation(stage), InfoSection.lines(Component.translatable("hud.bakery.blank_cake." + action + "_progress_hint").withStyle(ChatFormatting.GRAY),
                List.of(Component.translatable("hud.bakery.blank_cake." + action + "_progress", cake.getPresses(), cake.getPressesNeeded()).withStyle(ChatFormatting.GRAY))));
    }

    private static boolean describeFilling(Level level, BlankCakeStage stage, BlankCakeBlockEntity cake, List<InfoSection> sections) {
        switch (stage) {
            case SHEET -> {
                if (cake.getFilling() == null) {
                    sections.add(InfoSection.icons(Component.translatable("hud.bakery.blank_cake.fill"), fillings(level), InfoSection.GRID_COLUMNS));
                } else {
                    sections.add(InfoSection.title(Component.translatable("hud.bakery.blank_cake.roll_up_hint").withStyle(ChatFormatting.GRAY)));
                }
                return true;
            }
            case ROLL -> {
                sections.add(InfoSection.icons(Component.translatable("hud.bakery.blank_cake.cut"), List.of(new ItemStack(ObjectRegistry.BREAD_KNIFE.get())), InfoSection.ROW_COLUMNS));
                return true;
            }
            case SHELLS -> {
                if (cake.getShells().stream().anyMatch(shell -> shell.is(ObjectRegistry.CORNET_SHELL.get()))) {
                    sections.add(InfoSection.icons(Component.translatable("hud.bakery.blank_cake.fill"), fillings(level), InfoSection.GRID_COLUMNS));
                }
                sections.add(InfoSection.title(Component.translatable("hud.bakery.blank_cake.take_hint").withStyle(ChatFormatting.GRAY)));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private static List<ItemStack> fillings(Level level) {
        List<ItemStack> fillings = new ArrayList<>();
        fillings.add(new ItemStack(Items.MILK_BUCKET));
        level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get()).forEach(holder -> {
            ItemStack result = holder.value().getResult();
            if (fillings.stream().noneMatch(known -> ItemStack.isSameItem(known, result))) {
                fillings.add(result.copyWithCount(1));
            }
        });
        return fillings;
    }

    private static InfoSection onStation(BlankCakeStage stage) {
        ItemStack dough = new ItemStack(switch (stage) {
            case CAKE -> ObjectRegistry.CAKE_DOUGH.get();
            case SHEET, ROLL -> ObjectRegistry.SPONGE_SHEET.get();
            case SHELLS -> ObjectRegistry.CORNET_SHELL.get();
            default -> ObjectRegistry.SWEET_DOUGH.get();
        });
        return InfoSection.icons(Component.translatable("hud.bakery.on_station"), List.of(dough), InfoSection.ROW_COLUMNS);
    }

    private static String actionName(CakeAnimation animation) {
        return switch (animation) {
            case SPLIT -> "cut";
            case FLATTEN, KNEAD -> "roll";
            default -> "decorate";
        };
    }

    static boolean wearsDungarees() {
        if (!PlatformHelper.infoTooltipsNeedDungarees()) {
            return true;
        }
        Player player = Minecraft.getInstance().player;
        return player == null || BuiltInRegistries.ITEM.getKey(player.getItemBySlot(EquipmentSlot.LEGS).getItem()).equals(DUNGAREES);
    }
}
