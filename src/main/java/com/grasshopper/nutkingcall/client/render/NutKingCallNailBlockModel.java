package com.grasshopper.nutkingcall.client.render;

import com.grasshopper.nutkingcall.entity.NutKingCallNailEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;

public class NutKingCallNailBlockModel extends EntityModel<NutKingCallNailEntity> {

    public NutKingCallNailBlockModel() {
        texWidth = 8;
        texHeight = 8;
    }

    @Override
    public void setupAnim(NutKingCallNailEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {}
}