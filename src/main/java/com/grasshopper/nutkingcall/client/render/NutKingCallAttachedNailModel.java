package com.grasshopper.nutkingcall.client.render;

import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NutKingCallAttachedNailModel extends EntityModel<NutKingCallAttachedNailEntity> {

    private final ModelRenderer bone;

    public NutKingCallAttachedNailModel() {
        texWidth = 8;
        texHeight = 8;

        this.bone = new ModelRenderer(this);
        this.bone.setPos(0, 0, 0);

        ModelRenderer cube1 = new ModelRenderer(this);
        cube1.texOffs(3, 0).addBox(-0.196F, -0.366F, -0.366F, 0.392F, 0.733F, 0.733F, 0.1F, false);
        cube1.zRot = (float) Math.toRadians(-90);
        this.bone.addChild(cube1);

        ModelRenderer cube2 = new ModelRenderer(this);
        cube2.texOffs(0, 0).addBox(-0.159F, -0.310F, -0.171F, 0.364F, 1.479F, 0.341F, 0.0F, false);
        this.bone.addChild(cube2);

        ModelRenderer cube3 = new ModelRenderer(this);
        cube3.texOffs(0, 4).addBox(-0.118F, -0.150F, -0.171F, 0.3F, 0.3F, 0.3F, 0.0F, false);
        cube3.zRot = (float) Math.toRadians(-47.5F);
        this.bone.addChild(cube3);
    }

    @Override
    public void setupAnim(NutKingCallAttachedNailEntity entity, float limbSwing, float limbSwingAmount,
                          float inTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}