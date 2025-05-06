package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.satisfy.bakery.core.block.StreetSignBlock;
import net.satisfy.bakery.core.block.entity.StreetSignBlockEntity;

public class StreetSignBlockRenderer implements BlockEntityRenderer<StreetSignBlockEntity> {

    public StreetSignBlockRenderer() {
    }

    @Override
    public void render(StreetSignBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.0, 0.5);
        Direction facing = entity.getBlockState().getValue(StreetSignBlock.FACING);
        float rotation = facing == Direction.NORTH ? 180f : facing == Direction.WEST ? 90f : facing == Direction.EAST ? -90f : 0f;
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(rotation));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-22.5f));
        poseStack.scale(0.01f, -0.01f, 0.01f);
        Font font = Minecraft.getInstance().font;
        float[] offsetY = {15.0f, 35.0f, 50.0f};
        boolean glow = entity.isGlowing();
        for (int i = 0; i < 3; i++) {
            Component text = entity.getText(i);
            String str = text.getString();
            if (str.length() > 8) str = str.substring(0, 8);
            if (!str.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.0f, offsetY[i], 0.0f);
                if (glow) {
                    var seeThroughBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
                    font.drawInBatch(Component.literal(str).getVisualOrderText(), -font.width(str) / 2f, 0, 0xE8C992, false, poseStack.last().pose(), seeThroughBuffer, Font.DisplayMode.SEE_THROUGH, 0, 0xF000F0);
                    seeThroughBuffer.endBatch();
                } else {
                    font.drawInBatch(Component.literal(str).getVisualOrderText(), -font.width(str) / 2f, 0, 0xE8C992, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, light);
                }
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}
