package net.satisfy.bakery.core.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.farm_and_charm.core.block.SinkBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BrickSinkBlock extends SinkBlock {
    public BrickSinkBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
        int icingRed = 0xE3A6A0;
        int gold = 0xFFD700;

        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            tooltip.add(Component.translatable("tooltip.farm_and_charm.tooltip_information.hold", key).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
            return;
        }

        tooltip.add(Component.translatable("tooltip.farm_and_charm.sink.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.farm_and_charm.sink.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(icingRed))));
    }
}