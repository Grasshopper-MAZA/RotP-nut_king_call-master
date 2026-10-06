package com.grasshopper.nutkingcall.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class NutKingCallGiveNailAction extends StandEntityAction {

    public NutKingCallGiveNailAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ANY;
    }

    @Override
    protected ActionConditionResult checkTarget(ActionTarget target, LivingEntity user, IStandPower power) {
        if (target.getType() == ActionTarget.TargetType.ENTITY) {
            Entity targetEntity = target.getEntity();
            if (targetEntity instanceof StandEntity) {
                return ActionConditionResult.NEGATIVE;
            }
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            return;
        }

        if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
            Entity targetEntity = task.getTarget().getEntity();
            if (targetEntity instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) targetEntity;
                NutKingCallAttachedNailEntity.attachNail(world, livingTarget);
                NutKingCallAttachedNailEntity.attachNut(world, livingTarget);
            }
        } else if (task.getTarget().getType() == ActionTarget.TargetType.BLOCK) {
            BlockPos pos = task.getTarget().getBlockPos();
            Direction face = task.getTarget().getFace();
            if (face != null) {
                NutKingCallNailBlock.placeNail(world, pos, face);
                NutKingCallNailBlock.placeNut(world, pos, face);
            }
        }
    }
}