package net.satisfy.bakery.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.block.SmallCookingPotBlock;
import net.satisfy.bakery.core.block.entity.SmallCookingPotBlockEntity;
import net.satisfy.farm_and_charm.client.model.CraftingBowlModel;
import org.joml.Quaternionf;

import java.util.List;

public class JamPotRenderer implements BlockEntityRenderer<SmallCookingPotBlockEntity> {
    private static final ResourceLocation WHISK_TEXTURE = ResourceLocation.fromNamespaceAndPath("farm_and_charm", "textures/entity/crafting_bowl.png");
    private static final ResourceLocation JAM_TEXTURE = Bakery.identifier("block/jam_surface");
    private static final float INNER_MIN = 5.0F / 16.0F;
    private static final float INNER_MAX = 11.0F / 16.0F;
    private static final float FLOOR = 2.0F / 16.0F;
    private static final float FULL = 4.5F / 16.0F;

    private static final float DUMP_TICKS = 16.0F;
    private static final float DUMP_TILT = 70.0F;
    private static final float DUMP_LIFT = 0.25F;
    private static final float WOBBLE_ANGLE = 3.0F;
    private static final float WOBBLE_SPEED = 0.9F;
    private static final float SIMMER_ANGLE = 1.5F;
    private static final float SIMMER_SPEED = 0.15F;
    private static final float SIMMER_HOP = 0.006F;
    private static final float SHAKE_ANGLE = 2.5F;
    private static final float WHISK_TILT = -8.0F;
    private static final float WHISK_DROP = 2.0F / 16.0F;
    private static final float WHISK_PIVOT = 20.0F / 16.0F;

    private final ModelPart whisk;

    public JamPotRenderer(BlockEntityRendererProvider.Context context) {
        this.whisk = context.bakeLayer(CraftingBowlModel.LAYER_LOCATION).getChild("swing");
    }

    @Override
    public void render(SmallCookingPotBlockEntity pot, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        Level level = pot.getLevel();
        if (level == null) {
            return;
        }
        float time = level.getGameTime() + partialTick;

        poseStack.pushPose();
        move(pot, time, poseStack);
        BlockState state = pot.getBlockState();
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffers.getBuffer(RenderType.cutout()), state,
                Minecraft.getInstance().getBlockRenderer().getBlockModel(state), 1.0F, 1.0F, 1.0F, light, overlay);

        if (pot.isBurnt()) {
            drawSurface(poseStack, buffers, light, FULL, pot.getDisplayColor());
        } else if (pot.isCooking() || pot.isBottling()) {
            float progress = pot.getCookingTime() <= 0 ? 1.0F : Mth.clamp(pot.getTimer() / (float) pot.getCookingTime(), 0.0F, 1.0F);
            float surface = pot.isBottling()
                    ? Mth.lerp(Math.min(1.0F, pot.getJarsLeft() / 3.0F), FLOOR + 0.5F / 16.0F, FULL)
                    : FULL;
            drawSurface(poseStack, buffers, light, surface, pot.getDisplayColor());
            if (pot.isCooking() && progress < 0.6F) {
                drawFruit(pot, poseStack, buffers, light, overlay, level, surface, 1.0F - progress / 0.6F, time);
            }
        } else if (!pot.getIngredients().isEmpty()) {
            drawFruit(pot, poseStack, buffers, light, overlay, level, FLOOR, 1.0F, 0.0F);
        }

        drawWhisk(poseStack, buffers, light, overlay, pot.getWhiskAngle(partialTick), pot.getBlockState().getValue(SmallCookingPotBlock.FACING));
        poseStack.popPose();
    }

    private static void move(SmallCookingPotBlockEntity pot, float time, PoseStack poseStack) {
        poseStack.translate(0.5F, 0.0F, 0.5F);
        float sinceDump = time - pot.getLastDump();
        if (sinceDump >= 0.0F && sinceDump < DUMP_TICKS) {
            float tilt = Mth.sin(Mth.PI * sinceDump / DUMP_TICKS);
            Direction side = pot.getBlockState().getValue(SmallCookingPotBlock.FACING).getClockWise();
            poseStack.translate(0.0F, tilt * DUMP_LIFT, 0.0F);
            poseStack.mulPose(new Quaternionf().rotateAxis(tilt * DUMP_TILT * Mth.DEG_TO_RAD, side.getStepZ(), 0.0F, -side.getStepX()));
        } else if (!pot.getBlockState().getValue(SmallCookingPotBlock.LIT)) {
            poseStack.translate(-0.5F, 0.0F, -0.5F);
            return;
        } else if (pot.isStirDue()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(time * WOBBLE_SPEED) * WOBBLE_ANGLE));
        } else if (pot.isCooking()) {
            poseStack.translate(0.0F, Math.max(0.0F, Mth.sin(time * SIMMER_SPEED * 2.0F)) * SIMMER_HOP, 0.0F);
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(time * SIMMER_SPEED) * SIMMER_ANGLE));
        }
        float shake = pot.getWhiskSpeed() / SmallCookingPotBlockEntity.WHISK_MAX_SPEED;
        if (shake > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(time * 1.7F) * shake * SHAKE_ANGLE));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.cos(time * 1.3F) * shake * SHAKE_ANGLE));
        }
        poseStack.translate(-0.5F, 0.0F, -0.5F);
    }

    private static void drawSurface(PoseStack poseStack, MultiBufferSource buffers, int light, float y, int color) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(JAM_TEXTURE);
        VertexConsumer consumer = buffers.getBuffer(RenderType.cutout());
        PoseStack.Pose pose = poseStack.last();
        float r = (color >> 16 & 255) / 255.0F, g = (color >> 8 & 255) / 255.0F, b = (color & 255) / 255.0F;
        float u0 = sprite.getU(INNER_MIN), u1 = sprite.getU(INNER_MAX), v0 = sprite.getV(INNER_MIN), v1 = sprite.getV(INNER_MAX);
        vertex(pose, consumer, INNER_MIN, y, INNER_MIN, u0, v0, r, g, b, light);
        vertex(pose, consumer, INNER_MIN, y, INNER_MAX, u0, v1, r, g, b, light);
        vertex(pose, consumer, INNER_MAX, y, INNER_MAX, u1, v1, r, g, b, light);
        vertex(pose, consumer, INNER_MAX, y, INNER_MIN, u1, v0, r, g, b, light);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v, float r, float g, float b, int light) {
        consumer.addVertex(pose, x, y, z).setColor(r, g, b, 1.0F).setUv(u, v).setLight(light).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    private static void drawFruit(SmallCookingPotBlockEntity pot, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay, Level level, float y, float size, float time) {
        List<ItemStack> items = pot.getIngredients();
        for (int index = 0; index < items.size(); index++) {
            float angle = index * (360.0F / items.size()) + time * 0.6F;
            poseStack.pushPose();
            poseStack.translate(0.5, y + 0.01, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
            poseStack.translate(0.09, 0.0, 0.0);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            float scale = 0.22F * Math.max(0.2F, size);
            poseStack.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(items.get(index), ItemDisplayContext.FIXED, light, overlay, poseStack, buffers, level, index);
            poseStack.popPose();
        }
    }

    private void drawWhisk(PoseStack poseStack, MultiBufferSource buffers, int light, int overlay, float angle, Direction facing) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.translate(0.5F, -1.5F + WHISK_DROP, -0.5F);
        poseStack.mulPose(Axis.YP.rotation(angle));
        poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
        poseStack.translate(0.0F, WHISK_PIVOT, 0.0F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(WHISK_TILT));
        poseStack.translate(0.0F, -WHISK_PIVOT, 0.0F);
        whisk.render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(WHISK_TEXTURE)), light, overlay);
        poseStack.popPose();
    }
}
