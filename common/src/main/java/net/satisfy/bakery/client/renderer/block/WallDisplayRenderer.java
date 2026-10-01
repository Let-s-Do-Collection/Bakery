package net.satisfy.bakery.client.renderer.block;

import net.satisfy.foundation.storage.StorageTypeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.foundation.render.ClientUtil;

public class WallDisplayRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack poseStack, MultiBufferSource buffer, NonNullList<ItemStack> items) {
        float[][] itemPositions = {
                {-0.5f, 0.7f},
                {0.5f, 0.7f},
                {-0.5f, 1.8f},
                {0.5f, 1.8f}
        };

        float scale = 0.4f;

        for (int i = 0; i < Math.min(items.size(), 4); i++) {
            ItemStack stack = items.get(i);

            if (!stack.isEmpty()) {
                poseStack.pushPose();

                poseStack.scale(scale, scale, scale);

                float xOffset = itemPositions[i][0];
                float yOffset = itemPositions[i][1];
                float zBaseOffset = 0.5f;

                poseStack.translate(xOffset, yOffset, zBaseOffset);

                ClientUtil.renderItem(stack, poseStack, buffer, entity);

                poseStack.popPose();
            }
        }
    }
}
