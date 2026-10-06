package com.grasshopper.nutkingcall.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.pose.IModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.anim.barrage.StandTwoHandedBarrageAnimation;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class NutKingCallBarrageAnimation extends StandTwoHandedBarrageAnimation<NutKingCallEntity> {

    public NutKingCallBarrageAnimation(StandEntityModel<NutKingCallEntity> model,
                                       IModelPose<NutKingCallEntity> loop,
                                       IModelPose<NutKingCallEntity> recovery) {
        super(model, loop, recovery);
    }

    @Override
    public void animateSwing(NutKingCallEntity entity, StandEntityModel<NutKingCallEntity> model,
                             float loopCompletion, HandSide side, float yRotOffsetRad, float xRotRad, float zRotOffsetRad) {
        super.animateSwing(entity, model, loopCompletion, side, yRotOffsetRad, xRotRad, zRotOffsetRad);
        ModelRenderer arm = model.getArm(side);
        if (arm != null) {
            arm.xRot += xRotRad;
        }
    }
}