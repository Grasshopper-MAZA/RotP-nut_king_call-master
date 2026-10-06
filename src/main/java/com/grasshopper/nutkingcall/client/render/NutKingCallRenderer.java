package com.grasshopper.nutkingcall.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class NutKingCallRenderer extends StandEntityRenderer<NutKingCallEntity, StandEntityModel<NutKingCallEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            NutKingCallAddon.MOD_ID, "textures/entity/stand/nut_king_call.png");

    public NutKingCallRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(
                        new ResourceLocation(NutKingCallAddon.MOD_ID, "nut_king_call"),
                        NutKingCallModel::new),
                TEXTURE, 0);
    }
}