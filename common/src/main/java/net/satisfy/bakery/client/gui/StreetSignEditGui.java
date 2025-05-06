package net.satisfy.bakery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bakery.core.block.StreetSignBlock;
import net.satisfy.bakery.core.block.entity.StreetSignBlockEntity;
import net.satisfy.bakery.core.network.PacketHandler;
import net.satisfy.bakery.core.network.SetStreetSignTextPacket;

import java.util.ArrayList;
import java.util.List;

public class StreetSignEditGui extends Screen {
    private final StreetSignBlockEntity entity;
    private final BlockState state;
    private final List<EditBox> textFields = new ArrayList<>();

    public StreetSignEditGui(StreetSignBlockEntity entity) {
        super(Component.translatable("Edit Street Sign"));
        this.entity = entity;
        this.state = entity.getBlockState();
    }

    @Override
    protected void init() {
        for (int i = 0; i < 3; i++) {
            EditBox box = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 30 + i * 24, 200, 20, Component.literal(""));
            box.setValue(entity.getText(i).getString());
            box.setMaxLength(8);
            box.setBordered(false);
            this.addRenderableWidget(box);
            textFields.add(box);
        }
        this.setInitialFocus(textFields.get(0));
        this.addRenderableWidget(Button.builder(Component.translatable("Done"), button -> this.onClose())
                .bounds(this.width / 2 - 50, this.height - 40, 100, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);

        MultiBufferSource.BufferSource blockBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
        MultiBufferSource.BufferSource textBuffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(this.width / 2.0, this.height / 2.0 - 50, 100.0);
        poseStack.scale(160.0F, -160.0F, 160.0F);
        poseStack.translate(0.0D, -1.25D, 0D);
        poseStack.mulPose(Axis.XP.rotationDegrees(22.5f));

        poseStack.pushPose();
        poseStack.translate(-0.5, 0.4, -1);
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        blockRenderer.renderSingleBlock(state.getBlock().defaultBlockState().setValue(StreetSignBlock.FACING, Direction.SOUTH), poseStack, blockBuffer, 15728880, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        blockBuffer.endBatch();

        poseStack.pushPose();
        poseStack.translate(-0.5, 0.4, 1);
        RenderSystem.disableDepthTest();

        for (int i = 0; i < 3; i++) {
            String str = textFields.get(i).getValue();
            if (str.length() > 8) str = str.substring(0, 8);
            if (!str.isEmpty()) {
                Minecraft.getInstance().font.drawInBatch(str, -Minecraft.getInstance().font.width(str) / 2f, i * 0.1f, 0xE8C992, false, poseStack.last().pose(), textBuffer, Font.DisplayMode.NORMAL, 0, 15728880
                );
            }
        }

        textBuffer.endBatch();
        RenderSystem.enableDepthTest();
        poseStack.popPose();

        poseStack.popPose();
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 || keyCode == 257 || keyCode == 335) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        List<String> texts = textFields.stream().map(EditBox::getValue).toList();
        PacketHandler.sendToServer(new SetStreetSignTextPacket(entity.getBlockPos(), texts));
        super.onClose();
    }
}
