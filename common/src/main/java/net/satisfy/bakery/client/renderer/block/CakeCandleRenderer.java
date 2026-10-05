package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.platform.PlatformHelper;
import net.satisfy.bakery.core.block.PieBlock;
import net.satisfy.bakery.core.block.entity.CakeCandleBlockEntity;
import net.satisfy.foundation.client.render.WobbleAnimation;

public class CakeCandleRenderer implements BlockEntityRenderer<CakeCandleBlockEntity> {
    private static final float CUT_WOBBLE_STRENGTH = 0.6F;

    @Override
    public void render(CakeCandleBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = entity.getBlockState();
        if (!(state.getBlock() instanceof PieBlock pie)) {
            return;
        }
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        poseStack.pushPose();
        if (PlatformHelper.animationsEnabled()) {
            WobbleAnimation.apply(poseStack, entity.getLevel(), entity.getWobbleStart(), partialTicks, WobbleAnimation.seed(entity.getBlockPos().asLong()), CUT_WOBBLE_STRENGTH);
        }
        int pieces = entity.getPieces();
        for (Direction quarter : Direction.Plane.HORIZONTAL) {
            if (!PieBlock.hasPiece(pieces, quarter)) {
                continue;
            }
            BlockState piece = state.setValue(PieBlock.CUTS, 3).setValue(PieBlock.FACING, quarter).setValue(PieBlock.CANDLE_COUNT, 0).setValue(PieBlock.LIT, false);
            dispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(ItemBlockRenderTypes.getRenderType(piece, false)), piece, dispatcher.getBlockModel(piece), 1.0F, 1.0F, 1.0F, light, overlay);
        }
        int count = state.getValue(PieBlock.CANDLE_COUNT);
        if (count > 0) {
            BlockState candle = entity.getCandleBlock().defaultBlockState().setValue(CandleBlock.LIT, state.getValue(PieBlock.LIT));
            for (int index = 0; index < count; index++) {
                float[] spot = pie.candleSpot(state, index);
                poseStack.pushPose();
                poseStack.translate((spot[0] - 8.0F) / 16.0F, spot[2] / 16.0F, (spot[1] - 8.0F) / 16.0F);
                dispatcher.renderSingleBlock(candle, poseStack, buffer, light, overlay);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}
