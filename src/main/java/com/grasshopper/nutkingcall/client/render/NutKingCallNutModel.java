package com.grasshopper.nutkingcall.client.render;

import com.grasshopper.nutkingcall.entity.NutKingCallNutEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NutKingCallNutModel extends EntityModel<NutKingCallNutEntity> {

    private final ModelRenderer bone;

    public NutKingCallNutModel() {
        texWidth = 8;
        texHeight = 8;

        bone = new ModelRenderer(this);
        bone.setPos(0.0F, 0.0F, 0.0F);
        bone.texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(NutKingCallNutEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        bone.yRot = (float) Math.toRadians(-netHeadYaw);
        bone.xRot = (float) Math.toRadians(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}