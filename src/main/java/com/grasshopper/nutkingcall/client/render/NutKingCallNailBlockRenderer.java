package com.grasshopper.nutkingcall.client.render;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;
import com.grasshopper.nutkingcall.tileentity.NutKingCallNailTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class NutKingCallNailBlockRenderer extends TileEntityRenderer<NutKingCallNailTileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            NutKingCallAddon.MOD_ID, "textures/entity/nut_king_call_nail.png");

    private final ModelRenderer bone;

    public NutKingCallNailBlockRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);

        this.bone = new ModelRenderer(8, 8, 0, 0);
        this.bone.setPos(0, 0, 0);

        ModelRenderer cube1 = new ModelRenderer(8, 8, 0, 0);
        cube1.texOffs(3, 0).addBox(-0.196F, -0.366F, -0.366F, 0.392F, 0.733F, 0.733F, 0.1F, false);
        cube1.zRot = (float) Math.toRadians(90);
        this.bone.addChild(cube1);

        ModelRenderer cube2 = new ModelRenderer(8, 8, 0, 0);
        cube2.texOffs(0, 0).addBox(-0.159F, -0.310F, -0.171F, 0.364F, 1.479F, 0.341F, 0.0F, false);
        this.bone.addChild(cube2);

        ModelRenderer cube3 = new ModelRenderer(8, 8, 0, 0);
        cube3.texOffs(0, 4).addBox(-0.118F, -0.150F, -0.171F, 0.3F, 0.3F, 0.3F, 0.0F, false);
        cube3.zRot = (float) Math.toRadians(47.5);
        this.bone.addChild(cube3);
    }

    @Override
    public void render(NutKingCallNailTileEntity tileEntity, float partialTicks, MatrixStack matrixStack,
                       IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Direction facing = tileEntity.getBlockState().getValue(NutKingCallNailBlock.FACING);

        matrixStack.pushPose();
        matrixStack.translate(0.5, 0.5, 0.5);

        switch (facing) {
            case UP:
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
                break;
            case DOWN:
                break;
            case NORTH:
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(90));
                break;
            case SOUTH:
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90));
                break;
            case WEST:
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90));
                break;
            case EAST:
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90));
                break;
        }

        matrixStack.translate(0, 0.5, 0);
        IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.bone.render(matrixStack, builder, combinedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}