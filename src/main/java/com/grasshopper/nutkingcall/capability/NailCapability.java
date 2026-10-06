package com.grasshopper.nutkingcall.capability;

import java.util.UUID;

import com.grasshopper.nutkingcall.network.NutKingCallCapSyncPacket;
import com.grasshopper.nutkingcall.network.NutKingCallPackets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class NailCapability {

    private LivingEntity entity;

    private int entityId = -1;
    private boolean isInGround = false;
    private boolean wallClimbing = false;
    private BlockPos climbStartPos = BlockPos.ZERO;

    private int nailCount = 0;
    private int nutCount = 0;
    private int nailedCount = 0;

    private boolean pinned = false;
    private BlockPos pinPos = BlockPos.ZERO;
    private Direction pinFace = Direction.DOWN;
    private int struggleCount = 0;
    private int pinStartTick = 0;
    private UUID pinOwnerUUID = null;

    public NailCapability() {
    }

    public NailCapability(LivingEntity entity) {
        this.entity = entity;
        if (entity != null) {
            this.entityId = entity.getId();
        }
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
        if (entity != null) {
            this.entityId = entity.getId();
        }
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public boolean isInGround() {
        return isInGround;
    }

    public void setInGround(boolean inGround) {
        this.isInGround = inGround;
        syncData();
    }

    public boolean isWallClimbing() {
        return wallClimbing;
    }

    public void setWallClimbing(boolean wallClimbing) {
        this.wallClimbing = wallClimbing;
        syncData();
    }

    public BlockPos getClimbStartPos() {
        return climbStartPos;
    }

    public void setClimbStartPos(BlockPos climbStartPos) {
        this.climbStartPos = climbStartPos;
        syncData();
    }

    public int getNailCount() {
        return nailCount;
    }

    public void setNailCount(int nailCount) {
        this.nailCount = nailCount;
        updateNailed();
        syncData();
    }

    public void addNail() {
        this.nailCount++;
        updateNailed();
        syncData();
    }

    public void removeNail() {
        if (this.nailCount > 0) {
            this.nailCount--;
            updateNailed();
            syncData();
        }
    }

    public int getNutCount() {
        return nutCount;
    }

    public void setNutCount(int nutCount) {
        this.nutCount = nutCount;
        updateNailed();
        syncData();
    }

    public void addNut() {
        this.nutCount++;
        updateNailed();
        syncData();
    }

    public void removeNut() {
        if (this.nutCount > 0) {
            this.nutCount--;
            updateNailed();
            syncData();
        }
    }

    public int getNailedCount() {
        return nailedCount;
    }

    public void setNailedCount(int nailedCount) {
        this.nailedCount = nailedCount;
        syncData();
    }

    private void updateNailed() {
        this.nailedCount = Math.min(this.nailCount, this.nutCount);
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
        if (pinned && entity != null) {
            this.pinStartTick = entity.tickCount;
        }
        syncData();
    }

    public BlockPos getPinPos() {
        return pinPos;
    }

    public void setPinPos(BlockPos pinPos) {
        this.pinPos = pinPos;
        syncData();
    }

    public Direction getPinFace() {
        return pinFace;
    }

    public void setPinFace(Direction pinFace) {
        this.pinFace = pinFace;
        syncData();
    }

    public int getStruggleCount() {
        return struggleCount;
    }

    public void setStruggleCount(int struggleCount) {
        this.struggleCount = struggleCount;
        syncData();
    }

    public void incrementStruggle() {
        this.struggleCount++;
        syncData();
    }

    public int getPinStartTick() {
        return pinStartTick;
    }

    public void setPinStartTick(int pinStartTick) {
        this.pinStartTick = pinStartTick;
        syncData();
    }

    public UUID getPinOwnerUUID() {
        return pinOwnerUUID;
    }

    public void setPinOwnerUUID(UUID pinOwnerUUID) {
        this.pinOwnerUUID = pinOwnerUUID;
        syncData();
    }

    public void clearPinned() {
        this.pinned = false;
        this.pinPos = BlockPos.ZERO;
        this.pinFace = Direction.DOWN;
        this.struggleCount = 0;
        this.pinStartTick = 0;
        this.pinOwnerUUID = null;
        syncData();
    }

    public void tick() {
    }

    public void syncData() {
        if (entity != null && !entity.level.isClientSide()) {
            NutKingCallPackets.sendToClientsTrackingAndSelf(new NutKingCallCapSyncPacket(this), entity);
        }
    }

    public void deserializeFromPacket(boolean isInGround, boolean isWallClimbing, BlockPos climbStartPos,
                                      int nailCount, int nutCount,
                                      boolean pinned, BlockPos pinPos, Direction pinFace, int struggleCount,
                                      int pinStartTick, UUID pinOwnerUUID) {
        this.isInGround = isInGround;
        this.wallClimbing = isWallClimbing;
        this.climbStartPos = climbStartPos;
        this.nailCount = nailCount;
        this.nutCount = nutCount;
        this.nailedCount = Math.min(nailCount, nutCount);
        this.pinned = pinned;
        this.pinPos = pinPos;
        this.pinFace = pinFace;
        this.struggleCount = struggleCount;
        this.pinStartTick = pinStartTick;
        this.pinOwnerUUID = pinOwnerUUID;
    }

    public void copyFrom(NailCapability source) {
        this.entityId = source.entityId;
        this.isInGround = source.isInGround;
        this.wallClimbing = source.wallClimbing;
        this.climbStartPos = source.climbStartPos;
        this.nailCount = source.nailCount;
        this.nutCount = source.nutCount;
        this.nailedCount = source.nailedCount;
        this.pinned = source.pinned;
        this.pinPos = source.pinPos;
        this.pinFace = source.pinFace;
        this.struggleCount = source.struggleCount;
        this.pinStartTick = source.pinStartTick;
        this.pinOwnerUUID = source.pinOwnerUUID;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("EntityId", entityId);
        nbt.putBoolean("IsInGround", isInGround);
        nbt.putBoolean("IsWallClimbing", wallClimbing);
        nbt.putInt("ClimbX", climbStartPos.getX());
        nbt.putInt("ClimbY", climbStartPos.getY());
        nbt.putInt("ClimbZ", climbStartPos.getZ());
        nbt.putInt("NailCount", nailCount);
        nbt.putInt("NutCount", nutCount);
        nbt.putInt("NailedCount", nailedCount);
        nbt.putBoolean("Pinned", pinned);
        nbt.putInt("PinX", pinPos.getX());
        nbt.putInt("PinY", pinPos.getY());
        nbt.putInt("PinZ", pinPos.getZ());
        nbt.putInt("PinFace", pinFace.get3DDataValue());
        nbt.putInt("StruggleCount", struggleCount);
        nbt.putInt("PinStartTick", pinStartTick);
        if (pinOwnerUUID != null) {
            nbt.putUUID("PinOwner", pinOwnerUUID);
        }
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        entityId = nbt.getInt("EntityId");
        isInGround = nbt.getBoolean("IsInGround");
        wallClimbing = nbt.getBoolean("IsWallClimbing");
        climbStartPos = new BlockPos(nbt.getInt("ClimbX"), nbt.getInt("ClimbY"), nbt.getInt("ClimbZ"));
        nailCount = nbt.getInt("NailCount");
        nutCount = nbt.getInt("NutCount");
        nailedCount = nbt.getInt("NailedCount");
        pinned = nbt.getBoolean("Pinned");
        pinPos = new BlockPos(nbt.getInt("PinX"), nbt.getInt("PinY"), nbt.getInt("PinZ"));
        pinFace = Direction.from3DDataValue(nbt.getInt("PinFace"));
        struggleCount = nbt.getInt("StruggleCount");
        pinStartTick = nbt.getInt("PinStartTick");
        if (nbt.hasUUID("PinOwner")) {
            pinOwnerUUID = nbt.getUUID("PinOwner");
        } else {
            pinOwnerUUID = null;
        }
    }
}