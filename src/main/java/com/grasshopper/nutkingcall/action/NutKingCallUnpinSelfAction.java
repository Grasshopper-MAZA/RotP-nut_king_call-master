package com.grasshopper.nutkingcall.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class NutKingCallUnpinSelfAction extends StandEntityAction {

    public NutKingCallUnpinSelfAction(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) return;

        LivingEntity user = userPower.getUser();
        if (user == null) return;

        NailCapability cap = user.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap == null || !cap.isPinned()) return;

        NutKingCallAttachedNailEntity.unpin(world, user, true);
    }
}