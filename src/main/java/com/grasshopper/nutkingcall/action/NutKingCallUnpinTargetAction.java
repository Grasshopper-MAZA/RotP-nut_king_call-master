package com.grasshopper.nutkingcall.action;

import java.util.List;
import java.util.UUID;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NutKingCallUnpinTargetAction extends StandEntityAction {

    private static final double SEARCH_RANGE = 32.0D;

    public NutKingCallUnpinTargetAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.NONE;
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (findNearestPinnedTarget(user) == null) {
            return ActionConditionResult.NEGATIVE;
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) return;

        LivingEntity user = userPower.getUser();
        if (user == null) return;

        LivingEntity target = findNearestPinnedTarget(user);
        if (target == null) return;

        NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap != null && cap.isPinned()) {
            NutKingCallAttachedNailEntity.unpin(world, target, true);
            return;
        }

        boolean removedAny = false;
        for (NutKingCallAttachedNailEntity nail : world.getEntitiesOfClass(
                NutKingCallAttachedNailEntity.class,
                target.getBoundingBox().inflate(2.0D))) {
            if (nail.isPinned() && nail.getVehicle() == target) {
                BlockPos pinnedPos = nail.getPinnedBlockPos();
                if (pinnedPos != null) {
                    world.destroyBlock(pinnedPos, true);
                }
                nail.remove();
                removedAny = true;
            }
        }

        if (removedAny && cap != null) {
            cap.clearPinned();
        }
    }

    private LivingEntity findNearestPinnedTarget(LivingEntity user) {
        UUID ownerUUID = user.getUUID();
        AxisAlignedBB area = user.getBoundingBox().inflate(SEARCH_RANGE);

        List<LivingEntity> candidates = user.level.getEntitiesOfClass(
                LivingEntity.class, area,
                e -> e != user && e.isAlive() && e != user.getVehicle());

        LivingEntity nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (LivingEntity candidate : candidates) {
            NailCapability cap = candidate.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
            if (cap == null) continue;
            if (!cap.isPinned()) continue;

            UUID pinOwner = cap.getPinOwnerUUID();
            if (pinOwner != null && !pinOwner.equals(ownerUUID)) {
                continue;
            }

            double dist = candidate.distanceToSqr(user);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = candidate;
            }
        }

        return nearest;
    }
}