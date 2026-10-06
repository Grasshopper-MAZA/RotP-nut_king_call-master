package com.grasshopper.nutkingcall.action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class NutKingCallFlyingKickAction extends StandEntityAction {

    private static final double START_OFFSET = 2.0D;
    private static final double DASH_DISTANCE = 2.0D;
    private static final double HIT_RANGE = 2.5D;
    private static final float DAMAGE = 10.0F;
    private static final double KNOCKBACK = 1.5D;

    public NutKingCallFlyingKickAction(Builder builder) {
        super(builder);
    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (world.isClientSide()) return;
        if (phase != Phase.PERFORM) return;

        LivingEntity user = standPower.getUser();
        if (user == null) return;

        Vector3d look = user.getViewVector(1.0F).normalize();
        Vector3d flat = new Vector3d(look.x, 0, look.z);
        if (flat.lengthSqr() < 0.0001D) {
            flat = new Vector3d(0, 0, 1);
        }
        flat = flat.normalize();

        double startX = user.getX() + flat.x * START_OFFSET;
        double startY = user.getY() + user.getBbHeight() * 0.5;
        double startZ = user.getZ() + flat.z * START_OFFSET;
        standEntity.teleportTo(startX, startY, startZ);

        Vector3d dash = flat.scale(DASH_DISTANCE);
        standEntity.move(net.minecraft.entity.MoverType.SELF, dash);
        standEntity.setDeltaMovement(Vector3d.ZERO);
        standEntity.hurtMarked = true;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) return;

        LivingEntity user = userPower.getUser();
        if (user == null) return;

        AxisAlignedBB area = standEntity.getBoundingBox().inflate(HIT_RANGE);

        List<Entity> targets = world.getEntities(standEntity, area,
                e -> e instanceof LivingEntity && e != user && e != standEntity && e.isAlive());

        for (Entity entity : targets) {
            LivingEntity target = (LivingEntity) entity;
            target.hurt(DamageSource.mobAttack(standEntity), DAMAGE);

            Vector3d knockback = target.position().subtract(standEntity.position()).normalize().scale(KNOCKBACK);
            target.push(knockback.x, 0.4D, knockback.z);
            target.hurtMarked = true;
        }
    }

    @Nonnull
    @Override
    public StandPose getStandPose(IStandPower standPower, StandEntity standEntity, @Nonnull StandEntityTask task) {
        return new StandPose("new");
    }

    @Override
    protected boolean standMovesByItself(IStandPower standPower, StandEntity standEntity) {
        return true;
    }

    @Override
    public boolean lockStandManualMovement(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
}