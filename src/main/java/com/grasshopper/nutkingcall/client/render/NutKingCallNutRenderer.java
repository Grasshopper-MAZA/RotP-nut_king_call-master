package com.grasshopper.nutkingcall.client.render;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.entity.NutKingCallNutEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class NutKingCallNutRenderer extends SimpleEntityRenderer<NutKingCallNutEntity, NutKingCallNutModel> {

    public NutKingCallNutRenderer(EntityRendererManager renderManager) {
        super(renderManager, new NutKingCallNutModel(),
                new ResourceLocation(NutKingCallAddon.MOD_ID, "textures/entity/nut_king_call_nut.png"));
    }
}