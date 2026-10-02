package net.satisfy.bakery.client.gui.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.block.SmallCookingPotBlock;
import net.satisfy.bakery.core.block.entity.SmallCookingPotBlockEntity;
import net.satisfy.bakery.core.recipe.JamConsistency;
import net.satisfy.bakery.core.recipe.JamRecipe;
import net.satisfy.bakery.core.registry.RecipeTypeRegistry;
import net.satisfy.bakery.platform.PlatformHelper;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import net.satisfy.foundation.tooltip.TooltipBorder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JamPotInfoProvider implements BlockInfoProvider {
    private static final ResourceLocation STIR_ICON = Bakery.identifier("textures/gui/stir.png");
    private static final int STIR_ICON_SIZE = 20;
    private static final int STIR_START_COLOR = 0xFFFFD700;
    private static final int STIR_END_COLOR = 0xFFE0301E;

    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (hit == null || !PlatformHelper.showJamPotInfo() || !BakerStationInfoProvider.wearsDungarees()) {
            return List.of();
        }
        if (!(level.getBlockEntity(pos) instanceof SmallCookingPotBlockEntity pot)) {
            return List.of();
        }
        List<InfoSection> sections = new ArrayList<>();
        if (!state.getValue(SmallCookingPotBlock.LIT)) {
            sections.add(InfoSection.title(Component.translatable("hud.bakery.jam_pot.needs_heat").withStyle(ChatFormatting.RED)));
            return sections;
        }
        if (pot.isBurnt()) {
            sections.add(InfoSection.title(Component.translatable("hud.bakery.jam_pot.burnt").withStyle(ChatFormatting.RED)));
            return sections;
        }
        if (pot.isCooking()) {
            if (pot.isStirDue()) {
                int color = FastColor.ARGB32.lerp(pot.getStirUrgency(), STIR_START_COLOR, STIR_END_COLOR) & 0xFFFFFF;
                sections.add(InfoSection.image(Component.translatable("hud.bakery.jam_pot.stir").withStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(color))), STIR_ICON, STIR_ICON_SIZE));
            } else {
                sections.add(InfoSection.icons(cookingTitle(pot), List.of(pot.getResult().copyWithCount(1)), InfoSection.ROW_COLUMNS));
            }
            return sections;
        }
        if (pot.isBottling()) {
            sections.add(InfoSection.icons(Component.translatable("hud.bakery.jam_pot.take").withStyle(ChatFormatting.GREEN), List.of(pot.getResult().copyWithCount(pot.getJarsLeft())), InfoSection.ROW_COLUMNS));
            return sections;
        }
        if (!pot.getIngredients().isEmpty()) {
            List<ItemStack> next = pot.getNextIngredients();
            if (!next.isEmpty()) {
                sections.add(InfoSection.icons(Component.translatable("hud.bakery.jam_pot.next"), next, InfoSection.GRID_COLUMNS));
            }
            return sections;
        }
        List<ItemStack> start = new ArrayList<>();
        for (RecipeHolder<JamRecipe> holder : level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.JAM_COOKING_TYPE.get())) {
            ItemStack[] choices = holder.value().getIngredients().getFirst().getItems();
            if (choices.length > 0 && start.stream().noneMatch(known -> ItemStack.isSameItem(known, choices[0]))) {
                start.add(choices[0]);
            }
        }
        if (!start.isEmpty()) {
            sections.add(InfoSection.icons(Component.translatable("hud.bakery.jam_pot.start"), start, InfoSection.GRID_COLUMNS));
        }
        return sections;
    }

    @Override
    public void beforeBackground(Level level, BlockPos pos, BlockState state) {
        if (!(level.getBlockEntity(pos) instanceof SmallCookingPotBlockEntity pot) || !state.getValue(SmallCookingPotBlock.LIT) && !pot.isBottling()) {
            return;
        }
        if (pot.isBurnt()) {
            TooltipBorder.mark(0xF08C2414, 0xF0401008);
        } else if (pot.isCooking() && pot.isStirDue()) {
            TooltipBorder.mark(0xF0FFD700, 0xF0B8860B);
        } else if (pot.isBottling() && pot.getConsistency() != null) {
            markConsistency(pot.getConsistency());
        } else if (pot.isCooking() && pot.canBottle()) {
            markConsistency(pot.currentConsistency());
        }
    }

    private static void markConsistency(JamConsistency consistency) {
        switch (consistency) {
            case RUNNY -> TooltipBorder.mark(0xF07FD4F0, 0xF02E7FA8);
            case PERFECT -> TooltipBorder.mark(0xF06FD86A, 0xF02E8B2A);
            case CARAMELIZED -> TooltipBorder.mark(0xF0F0A030, 0xF0A0581A);
        }
    }

    private static Component cookingTitle(SmallCookingPotBlockEntity pot) {
        if (!pot.canBottle()) {
            return Component.translatable("hud.bakery.jam_pot.cooking");
        }
        return switch (pot.currentConsistency()) {
            case RUNNY -> Component.translatable("hud.bakery.jam_pot.runny").withStyle(ChatFormatting.AQUA);
            case PERFECT -> Component.translatable("hud.bakery.jam_pot.ready").withStyle(ChatFormatting.GREEN);
            case CARAMELIZED -> Component.translatable("hud.bakery.jam_pot.caramelized").withStyle(ChatFormatting.GOLD);
        };
    }
}
