package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.satisfy.bakery.core.block.entity.BakerStationBlockEntity;

public class BakerStationRenderer implements BlockEntityRenderer<BakerStationBlockEntity> {
    private static final float[][] SLOTS = {
            {0.22F, 0.2F, 15.0F},
            {0.8F, 0.5F, 100.0F}
    };

    @Override
    public void render(BakerStationBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        if (entity.getLevel() == null) {
            return;
        }
        Direction facing = entity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        int topLight = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above());

        for (int slot = 0; slot < BakerStationBlockEntity.TOOL_SLOTS; slot++) {
            ItemStack tool = entity.getTool(slot);
            if (tool.isEmpty()) {
                continue;
            }
            poseStack.pushPose();
            poseStack.translate(0.5, 0.0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - facing.toYRot()));
            poseStack.translate(SLOTS[slot][0] - 0.5, 1.02, SLOTS[slot][1] - 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(SLOTS[slot][2]));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            Minecraft.getInstance().getItemRenderer().renderStatic(tool, ItemDisplayContext.FIXED, topLight, overlay, poseStack, buffers, entity.getLevel(), slot);
            poseStack.popPose();
        }
    }
}
