package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.PieBlock;
import net.satisfy.bakery.core.block.entity.CakeCandleBlockEntity;

public class CakeCandleRenderer implements BlockEntityRenderer<CakeCandleBlockEntity> {

    @Override
    public void render(CakeCandleBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = entity.getBlockState();
        if (!(state.getBlock() instanceof PieBlock pie)) {
            return;
        }
        int count = state.getValue(PieBlock.CANDLE_COUNT);
        if (count == 0) {
            return;
        }
        BlockState candle = entity.getCandleBlock().defaultBlockState().setValue(CandleBlock.LIT, state.getValue(PieBlock.LIT));
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        for (int index = 0; index < count; index++) {
            float[] spot = pie.candleSpot(state, index);
            poseStack.pushPose();
            poseStack.translate((spot[0] - 8.0F) / 16.0F, spot[2] / 16.0F, (spot[1] - 8.0F) / 16.0F);
            dispatcher.renderSingleBlock(candle, poseStack, buffer, light, overlay);
            poseStack.popPose();
        }
    }
}
