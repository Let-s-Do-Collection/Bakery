package net.satisfy.bakery.client.renderer.block;

import net.satisfy.foundation.storage.StorageTypeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.satisfy.foundation.storage.StorageBlockEntity;

@Environment(EnvType.CLIENT)
public class CakeDisplayRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        int invSize = 6;
        float scale = 0.35f;
        float itemScaleY = 0.4f;
        float yOffsetFirstColumn = 1.15f;
        float yOffsetSecondColumn = 0.25f;
        float zOffset = -0.45f;
        float xOffsetModifier = 2.1f;
        float secondColumnAdditionalOffset = -1.5f;
        float firstColumnLeftShift = 1.2f;

        for (int i = 0; i < invSize; i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && !(stack.getItem() instanceof BlockItem)) {
                matrices.pushPose();
                matrices.scale(scale, itemScaleY, scale);

                boolean isFirstColumn = i < invSize / 2;
                float xOffset = (i - 1) * xOffsetModifier + (isFirstColumn ? firstColumnLeftShift : secondColumnAdditionalOffset);
                float yOffset = isFirstColumn ? yOffsetFirstColumn : yOffsetSecondColumn;

                matrices.translate(xOffset, yOffset, zOffset);
                matrices.mulPose(Axis.XP.rotationDegrees(90f));

                matrices.translate(-1.2f * i, 1, 0);
                StackedDisplayItems.renderFlatPile(stack, i, 0.0625f, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
