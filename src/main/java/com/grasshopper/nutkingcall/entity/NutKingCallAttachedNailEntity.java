package com.grasshopper.nutkingcall.entity;

import java.util.List;

import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;
import com.grasshopper.nutkingcall.init.InitEntities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class NutKingCallAttachedNailEntity extends Entity {

    private static final int CLICKS_TO_REMOVE = 3;
    private static final int PIN_REMOVE_CLICKS = 10;
    private static final int BLEED_DURATION_TICKS = 100;
    private static final int BLEED_INTERVAL_TICKS = 20;
    private static final float BLEED_DAMAGE = 1.0F;

    private int clickCount;
    private boolean isNut;
    private boolean isPinned;
    private BlockPos pinnedBlockPos;

    public NutKingCallAttachedNailEntity(EntityType<? extends NutKingCallAttachedNailEntity> type, World world) {
        super(type, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public NutKingCallAttachedNailEntity(World world, LivingEntity target, boolean isNut) {
        super(InitEntities.NUT_KING_CALL_ATTACHED_NAIL.get(), world);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.isNut = isNut;
        if (target != null) {
            setPos(target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) return;

        Entity vehicle = getVehicle();

        if (vehicle == null) {
            remove();
            return;
        }

        if (vehicle instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) vehicle;
            if (!target.isAlive()) {
                remove();
                return;
            }

            if (!isPinned && !isNut
                    && this.tickCount <= BLEED_DURATION_TICKS
                    && this.tickCount % BLEED_INTERVAL_TICKS == 0) {
                target.hurt(DamageSource.GENERIC, BLEED_DAMAGE);
            }
        }
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (level.isClientSide()) {
            return ActionResultType.SUCCESS;
        }
        clickCount++;
        int required = isPinned ? PIN_REMOVE_CLICKS : CLICKS_TO_REMOVE;
        if (clickCount >= required) {
            removeNail(player);
        }
        return ActionResultType.SUCCESS;
    }

    private void removeNail(PlayerEntity player) {
        if (isPinned && pinnedBlockPos != null) {
            level.destroyBlock(pinnedBlockPos, true);
        }

        Entity vehicle = getVehicle();
        if (vehicle instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) vehicle;
            NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
            if (cap != null) {
                if (isNut) cap.removeNut();
                else cap.removeNail();
                cap.clearPinned();
            }
        }

        remove();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        clickCount = nbt.getInt("ClickCount");
        isNut = nbt.getBoolean("IsNut");
        isPinned = nbt.getBoolean("IsPinned");
        if (nbt.contains("PinnedBlockX")) {
            pinnedBlockPos = new BlockPos(
                    nbt.getInt("PinnedBlockX"),
                    nbt.getInt("PinnedBlockY"),
                    nbt.getInt("PinnedBlockZ"));
        } else {
            pinnedBlockPos = null;
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("ClickCount", clickCount);
        nbt.putBoolean("IsNut", isNut);
        nbt.putBoolean("IsPinned", isPinned);
        if (pinnedBlockPos != null) {
            nbt.putInt("PinnedBlockX", pinnedBlockPos.getX());
            nbt.putInt("PinnedBlockY", pinnedBlockPos.getY());
            nbt.putInt("PinnedBlockZ", pinnedBlockPos.getZ());
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public BlockPos getPinnedBlockPos() {
        return pinnedBlockPos;
    }

    public static void attachNail(World world, LivingEntity target) {
        NutKingCallAttachedNailEntity nail = new NutKingCallAttachedNailEntity(world, target, false);
        world.addFreshEntity(nail);
        boolean riding = nail.startRiding(target, true);
        if (!riding) {
            nail.remove();
            return;
        }
        NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap != null) {
            cap.addNail();
        }
    }

    public static void attachNut(World world, LivingEntity target) {
        NutKingCallAttachedNailEntity nut = new NutKingCallAttachedNailEntity(world, target, true);
        world.addFreshEntity(nut);
        boolean riding = nut.startRiding(target, true);
        if (!riding) {
            nut.remove();
            return;
        }
        NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap != null) {
            cap.addNut();
        }
    }

    public static void pinEntity(World world, LivingEntity target) {
        pinEntity(world, target, null);
    }

    public static void pinEntity(World world, LivingEntity target, LivingEntity owner) {
        NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap == null) return;

        Direction pinFace = findPinFace(world, target);
        BlockPos pinPos = findPinPos(world, target, pinFace);

        NutKingCallAttachedNailEntity nail = new NutKingCallAttachedNailEntity(world, target, false);
        nail.isPinned = true;
        nail.pinnedBlockPos = pinPos;
        nail.setPos(pinPos.getX() + 0.5, pinPos.getY() + 0.5, pinPos.getZ() + 0.5);
        world.addFreshEntity(nail);
        nail.startRiding(target, true);

        cap.setPinned(true);
        cap.setPinPos(pinPos);
        cap.setPinFace(pinFace);
        cap.setStruggleCount(0);
        cap.setPinOwnerUUID(owner != null ? owner.getUUID() : null);
    }

    public static void unpin(World world, LivingEntity target, boolean destroyBlock) {
        NailCapability cap = target.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap == null || !cap.isPinned()) return;

        BlockPos pinPos = cap.getPinPos();

        List<NutKingCallAttachedNailEntity> nails = world.getEntitiesOfClass(
                NutKingCallAttachedNailEntity.class,
                target.getBoundingBox().inflate(2.0D));
        for (NutKingCallAttachedNailEntity nail : nails) {
            if (nail.isPinned() && nail.getVehicle() == target) {
                nail.remove();
            }
        }

        cap.clearPinned();

        if (destroyBlock && pinPos != null && !pinPos.equals(BlockPos.ZERO)) {
            world.destroyBlock(pinPos, true);
        }
    }

    public static void pinBlock(World world, BlockPos pos, Direction face) {
        NutKingCallAttachedNailEntity nail = new NutKingCallAttachedNailEntity(
                InitEntities.NUT_KING_CALL_ATTACHED_NAIL.get(), world);
        nail.isPinned = true;
        nail.pinnedBlockPos = pos;
        nail.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        world.addFreshEntity(nail);
    }

    private static Direction findPinFace(World world, LivingEntity target) {
        BlockPos pos = target.blockPosition();
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.relative(dir);
            BlockState state = world.getBlockState(neighbor);
            if (state.isFaceSturdy(world, neighbor, dir.getOpposite())) {
                return dir;
            }
        }
        return Direction.DOWN;
    }

    private static BlockPos findPinPos(World world, LivingEntity target, Direction face) {
        BlockPos pos = target.blockPosition();
        if (face == Direction.DOWN) {
            return pos;
        }
        return pos.relative(face);
    }
}