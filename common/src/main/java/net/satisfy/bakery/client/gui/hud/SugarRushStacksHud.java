package net.satisfy.bakery.client.gui.hud;

import com.google.common.collect.Ordering;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.satisfy.bakery.core.registry.MobEffectRegistry;

public final class SugarRushStacksHud {
    private static final int ICON_STEP = 25;
    private static final int HARMFUL_ROW = 26;
    private static final int DEMO_OFFSET = 15;
    private static final int ICON_RIGHT = 22;
    private static final int TEXT_TOP = 14;

    private SugarRushStacksHud() {
    }

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui) {
            return;
        }
        if (minecraft.screen instanceof EffectRenderingInventoryScreen<?> screen && screen.canSeeEffects()) {
            return;
        }
        int beneficial = 0;
        int harmful = 0;
        for (MobEffectInstance instance : Ordering.natural().reverse().sortedCopy(minecraft.player.getActiveEffects())) {
            if (!instance.showIcon()) {
                continue;
            }
            int x = graphics.guiWidth();
            int y = minecraft.isDemo() ? 1 + DEMO_OFFSET : 1;
            if (instance.getEffect().value().isBeneficial()) {
                x -= ICON_STEP * ++beneficial;
            } else {
                x -= ICON_STEP * ++harmful;
                y += HARMFUL_ROW;
            }
            int stacks = instance.getAmplifier() + 1;
            if (!instance.is(MobEffectRegistry.SUGAR_RUSH) || stacks < 2) {
                continue;
            }
            String text = String.valueOf(stacks);
            graphics.pose().pushPose();
            graphics.pose().translate(0.0F, 0.0F, 200.0F);
            graphics.drawString(minecraft.font, text, x + ICON_RIGHT - minecraft.font.width(text), y + TEXT_TOP, 0xFFFFFF, true);
            graphics.pose().popPose();
        }
    }
}
