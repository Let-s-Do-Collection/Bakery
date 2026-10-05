package net.satisfy.bakery.client.renderer.block;

import net.satisfy.foundation.storage.StorageTypeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bakery.core.block.StackingStorageBlock;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.foundation.render.ClientUtil;

public class WallDisplayRenderer implements StorageTypeRenderer {
    private static final float[][] ITEM_POSITIONS = {
            {-0.5f, 0.7f},
            {0.5f, 0.7f},
            {-0.5f, 1.8f},
            {0.5f, 1.8f}
    };
    private static final float SCALE = 0.4f;
    private static final int MAX_LAYERS = 4;
    private static final int ITEMS_PER_LAYER = (StackingStorageBlock.MAX_STACK + MAX_LAYERS - 1) / MAX_LAYERS;
    private static final float[] LAYER_SHIFT_X = {0.0f, 0.12f, -0.1f, 0.06f};
    private static final float[] LAYER_SHIFT_Y = {0.0f, 0.06f, 0.1f, 0.16f};
    private static final float[] LAYER_ROLL = {0.0f, -9.0f, 7.0f, -4.0f};
    private static final float LAYER_FORWARD = -0.06f;

    @Override
    public void render(StorageBlockEntity entity, PoseStack poseStack, MultiBufferSource buffer, NonNullList<ItemStack> items) {
        for (int i = 0; i < Math.min(items.size(), ITEM_POSITIONS.length); i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            int layers = Math.min(MAX_LAYERS, 1 + (stack.getCount() - 1) / ITEMS_PER_LAYER);
            for (int layer = 0; layer < layers; layer++) {
                poseStack.pushPose();
                poseStack.scale(SCALE, SCALE, SCALE);
                poseStack.translate(ITEM_POSITIONS[i][0] + LAYER_SHIFT_X[layer], ITEM_POSITIONS[i][1] + LAYER_SHIFT_Y[layer], 0.5f + layer * LAYER_FORWARD);
                poseStack.mulPose(Axis.ZP.rotationDegrees(LAYER_ROLL[layer]));
                ClientUtil.renderItem(stack, poseStack, buffer, entity);
                poseStack.popPose();
            }
        }
    }
}
