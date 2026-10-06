package com.grasshopper.nutkingcall.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EmptyRenderer<T extends Entity> extends EntityRenderer<T> {

    private static final ResourceLocation EMPTY_TEXTURE =
            new ResourceLocation("minecraft", "textures/misc/white.png");

    public EmptyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return EMPTY_TEXTURE;
    }
}