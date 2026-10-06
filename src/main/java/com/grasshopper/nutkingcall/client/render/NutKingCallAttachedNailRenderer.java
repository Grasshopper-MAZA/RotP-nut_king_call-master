package com.grasshopper.nutkingcall.client.render;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class NutKingCallAttachedNailRenderer extends SimpleEntityRenderer<NutKingCallAttachedNailEntity, NutKingCallAttachedNailModel> {

    public NutKingCallAttachedNailRenderer(EntityRendererManager renderManager) {
        super(renderManager, new NutKingCallAttachedNailModel(),
                new ResourceLocation(NutKingCallAddon.MOD_ID, "textures/entity/nut_king_call_nail.png"));
    }
}