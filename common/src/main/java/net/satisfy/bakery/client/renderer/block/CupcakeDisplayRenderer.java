package net.satisfy.bakery.client.renderer.block;

import net.satisfy.foundation.storage.StorageTypeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.foundation.render.ClientUtil;

@Environment(EnvType.CLIENT)
public class CupcakeDisplayRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);

            if (stack.isEmpty()) continue;

            matrices.pushPose();

            matrices.scale(0.3f, 0.3f, 0.3f);

            float xOffset = (i % 3) * 2.1f;
            float yOffset = i < 3 ? 1.7f : 0.45f;
            float zOffset = -1f;

            xOffset += i < 3 ? -0.9f : 2.7f;

            matrices.translate(xOffset, yOffset, zOffset);
            matrices.mulPose(Axis.XP.rotationDegrees(90f));
            matrices.translate(-1.2f * i, 1, 0);

            ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);

            matrices.popPose();
        }
    }
}
