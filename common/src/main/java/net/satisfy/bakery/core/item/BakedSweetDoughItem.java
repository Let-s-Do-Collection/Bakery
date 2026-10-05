package net.satisfy.bakery.core.item;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.bakery.client.util.ClientPlayerName;

import java.util.List;

public class BakedSweetDoughItem extends Item {
    private static final int ICING_PINK = 0xE3A6A0;

    public BakedSweetDoughItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (Platform.getEnvironment() == Env.CLIENT) {
            tooltip.add(Component.translatable("tooltip.bakery.baked_sweet_dough", ClientPlayerName.get()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(ICING_PINK)).withItalic(true)));
        }
    }
}
