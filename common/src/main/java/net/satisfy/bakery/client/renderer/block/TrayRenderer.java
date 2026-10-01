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
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.foundation.render.ClientUtil;

@Environment(EnvType.CLIENT)
public class TrayRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        matrices.translate(0f, 0.3, 0.2f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                if (stack.getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() == ObjectRegistry.CRUSTY_BREAD_BLOCK.get()) {
                        matrices.translate(-0.6f, -0.5f, -1f);
                        matrices.scale(2f, 2f, 2f);
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else if (blockItem.getBlock() == ObjectRegistry.BUN_BLOCK.get()) {
                        matrices.translate(-0.6f, -0.5f, -1f);
                        matrices.scale(2f, 2f, 2f);
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else if (blockItem.getBlock() == ObjectRegistry.BREAD_BLOCK.get()) {
                        matrices.translate(-0.6f, -0.5f, 1.0f);
                        matrices.scale(2f, 2f, 2f);
                        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else if (blockItem.getBlock() == ObjectRegistry.BRAIDED_BREAD_BLOCK.get()) {
                        matrices.translate(-0.6f, -0.5f, 1.0f);
                        matrices.scale(2f, 2f, 2f);
                        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else if (blockItem.getBlock() == ObjectRegistry.TOAST_BLOCK.get()) {
                        matrices.translate(-0.6f, -0.5f, 1.0f);
                        matrices.scale(2f, 2f, 2f);
                        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else if (blockItem.getBlock() == ObjectRegistry.BAGUETTE_BLOCK.get()) {
                        matrices.translate(-0.3f, -0.5f, 0.7f);
                        matrices.scale(1.4f, 1.4f, 1.4f);
                        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    } else {
                        matrices.translate(0.1, -0.5f, -0.4f);
                        matrices.scale(0.8f, 0.8f, 0.8f);
                        ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                    }
                } else {
                    matrices.translate(0.2f * i, 0, 0);
                    matrices.mulPose(Axis.YN.rotationDegrees(66.0f));
                    ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                }
                matrices.popPose();
            }
        }
    }
}