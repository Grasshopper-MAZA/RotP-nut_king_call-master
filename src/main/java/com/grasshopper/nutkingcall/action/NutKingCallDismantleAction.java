package com.grasshopper.nutkingcall.action;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;
import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class NutKingCallDismantleAction extends StandEntityAction {

    public NutKingCallDismantleAction(Builder builder) {
        super(builder);
    }

    @Nonnull
    @Override
    protected ResourceLocation getIconTexturePath(@Nullable IStandPower power) {
        return new ResourceLocation(NutKingCallAddon.MOD_ID, "textures/action/nut_king_call_dismantle.png");
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

        Vector3d center = user.position();
        AxisAlignedBB area = new AxisAlignedBB(center, center).inflate(8.0D);

        removeAttachedNails(world, user, area);
        removePinnedBlocks(world, area);
        removeNailBlocks(world, area);
    }

    private void removeAttachedNails(World world, LivingEntity user, AxisAlignedBB area) {
        List<Entity> entities = world.getEntities(user, area);
        for (Entity entity : entities) {
            if (entity instanceof NutKingCallAttachedNailEntity) {
                NutKingCallAttachedNailEntity nail = (NutKingCallAttachedNailEntity) entity;
                if (nail.isPinned()) {
                    continue;
                }
                Entity vehicle = entity.getVehicle();
                if (vehicle instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) vehicle;
                    NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
                    if (cap != null) {
                        cap.removeNail();
                        cap.removeNut();
                    }
                }
                entity.remove();
            }
        }
    }

    private void removePinnedBlocks(World world, AxisAlignedBB area) {
        List<NutKingCallAttachedNailEntity> nails = world.getEntitiesOfClass(
                NutKingCallAttachedNailEntity.class, area);
        for (NutKingCallAttachedNailEntity nail : nails) {
            if (!nail.isPinned()) continue;
            BlockPos pinnedBlockPos = nail.getPinnedBlockPos();
            if (pinnedBlockPos != null) {
                world.destroyBlock(pinnedBlockPos, true);
            }
            nail.remove();
        }
    }

    private void removeNailBlocks(World world, AxisAlignedBB area) {
        BlockPos min = new BlockPos(area.minX, area.minY, area.minZ);
        BlockPos max = new BlockPos(area.maxX, area.maxY, area.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof NutKingCallNailBlock) {
                NutKingCallNailBlock.dismantleNail(world, pos);
            }
        }
    }
}