package com.grasshopper.nutkingcall.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class NutKingCallPinAction extends StandEntityAction {

    public NutKingCallPinAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    protected ActionConditionResult checkTarget(ActionTarget target, LivingEntity user, IStandPower power) {
        Entity targetEntity = target.getEntity();
        if (!(targetEntity instanceof LivingEntity)) {
            return ActionConditionResult.NEGATIVE;
        }
        if (targetEntity instanceof StandEntity) {
            return ActionConditionResult.NEGATIVE;
        }
        if (targetEntity instanceof NutKingCallAttachedNailEntity) {
            return ActionConditionResult.NEGATIVE;
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) return;

        Entity targetEntity = task.getTarget().getEntity();
        if (targetEntity instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) targetEntity;
            NutKingCallAttachedNailEntity.pinEntity(world, livingTarget, userPower.getUser());
        }
    }
}