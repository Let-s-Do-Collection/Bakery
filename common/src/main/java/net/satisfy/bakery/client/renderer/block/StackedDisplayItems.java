package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.satisfy.bakery.core.block.StackingStorageBlock;
import net.satisfy.foundation.render.ClientUtil;
import net.satisfy.foundation.storage.StorageBlockEntity;

public final class StackedDisplayItems {
    private static final int ITEMS_PER_LAYER = 8;
    private static final int MAX_LAYERS = StackingStorageBlock.MAX_STACK / ITEMS_PER_LAYER;
    private static final float[] LAYER_SHIFT_X = {0.0f, 0.06f, -0.05f, 0.04f, -0.07f, 0.02f, 0.07f, -0.03f};
    private static final float[] LAYER_SHIFT_Y = {0.0f, -0.04f, 0.05f, 0.02f, -0.06f, 0.07f, -0.02f, 0.04f};
    private static final float[] LAYER_YAW = {0.0f, 23.0f, -17.0f, 41.0f, -32.0f, 12.0f, -46.0f, 29.0f};

    private StackedDisplayItems() {
    }

    public static void renderFlatPile(ItemStack stack, int slot, float layerRise, PoseStack matrices, MultiBufferSource buffers, StorageBlockEntity entity) {
        int layers = Math.min(MAX_LAYERS, 1 + (stack.getCount() - 1) / ITEMS_PER_LAYER);
        for (int layer = 0; layer < layers; layer++) {
            int variant = layer == 0 ? 0 : (slot * 3 + layer) % LAYER_YAW.length;
            matrices.pushPose();
            matrices.translate(LAYER_SHIFT_X[variant], LAYER_SHIFT_Y[variant], -layer * layerRise);
            matrices.mulPose(Axis.ZP.rotationDegrees(LAYER_YAW[variant]));
            ClientUtil.renderItem(stack, matrices, buffers, entity);
            matrices.popPose();
        }
    }
}
