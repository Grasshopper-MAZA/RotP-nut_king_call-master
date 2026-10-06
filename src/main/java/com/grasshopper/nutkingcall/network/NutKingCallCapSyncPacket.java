package com.grasshopper.nutkingcall.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class NutKingCallCapSyncPacket {

    private final int entityId;
    private final boolean isInGround;
    private final boolean isWallClimbing;
    private final BlockPos climbStartPos;
    private final int nailCount;
    private final int nutCount;
    private final boolean pinned;
    private final BlockPos pinPos;
    private final Direction pinFace;
    private final int struggleCount;
    private final int pinStartTick;
    private final UUID pinOwnerUUID;

    public NutKingCallCapSyncPacket(NailCapability cap) {
        this.entityId = cap.getEntityId();
        this.isInGround = cap.isInGround();
        this.isWallClimbing = cap.isWallClimbing();
        this.climbStartPos = cap.getClimbStartPos();
        this.nailCount = cap.getNailCount();
        this.nutCount = cap.getNutCount();
        this.pinned = cap.isPinned();
        this.pinPos = cap.getPinPos();
        this.pinFace = cap.getPinFace();
        this.struggleCount = cap.getStruggleCount();
        this.pinStartTick = cap.getPinStartTick();
        this.pinOwnerUUID = cap.getPinOwnerUUID();
    }

    public NutKingCallCapSyncPacket(int entityId, boolean isInGround, boolean isWallClimbing, BlockPos climbStartPos,
                                    int nailCount, int nutCount,
                                    boolean pinned, BlockPos pinPos, Direction pinFace, int struggleCount,
                                    int pinStartTick, UUID pinOwnerUUID) {
        this.entityId = entityId;
        this.isInGround = isInGround;
        this.isWallClimbing = isWallClimbing;
        this.climbStartPos = climbStartPos;
        this.nailCount = nailCount;
        this.nutCount = nutCount;
        this.pinned = pinned;
        this.pinPos = pinPos;
        this.pinFace = pinFace;
        this.struggleCount = struggleCount;
        this.pinStartTick = pinStartTick;
        this.pinOwnerUUID = pinOwnerUUID;
    }

    public static class Handler implements IModPacketHandler<NutKingCallCapSyncPacket> {

        @Override
        public void encode(NutKingCallCapSyncPacket packet, PacketBuffer buf) {
            buf.writeInt(packet.entityId);
            buf.writeBoolean(packet.isInGround);
            buf.writeBoolean(packet.isWallClimbing);
            buf.writeInt(packet.climbStartPos.getX());
            buf.writeInt(packet.climbStartPos.getY());
            buf.writeInt(packet.climbStartPos.getZ());
            buf.writeInt(packet.nailCount);
            buf.writeInt(packet.nutCount);
            buf.writeBoolean(packet.pinned);
            buf.writeInt(packet.pinPos.getX());
            buf.writeInt(packet.pinPos.getY());
            buf.writeInt(packet.pinPos.getZ());
            buf.writeInt(packet.pinFace.get3DDataValue());
            buf.writeInt(packet.struggleCount);
            buf.writeInt(packet.pinStartTick);
            buf.writeBoolean(packet.pinOwnerUUID != null);
            if (packet.pinOwnerUUID != null) {
                buf.writeUUID(packet.pinOwnerUUID);
            }
        }

        @Override
        public NutKingCallCapSyncPacket decode(PacketBuffer buf) {
            int entityId = buf.readInt();
            boolean isInGround = buf.readBoolean();
            boolean isWallClimbing = buf.readBoolean();
            BlockPos climbStartPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            int nailCount = buf.readInt();
            int nutCount = buf.readInt();
            boolean pinned = buf.readBoolean();
            BlockPos pinPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            Direction pinFace = Direction.from3DDataValue(buf.readInt());
            int struggleCount = buf.readInt();
            int pinStartTick = buf.readInt();
            UUID pinOwnerUUID = null;
            if (buf.readBoolean()) {
                pinOwnerUUID = buf.readUUID();
            }
            return new NutKingCallCapSyncPacket(entityId, isInGround, isWallClimbing, climbStartPos,
                    nailCount, nutCount, pinned, pinPos, pinFace, struggleCount, pinStartTick, pinOwnerUUID);
        }

        @Override
        public void handle(NutKingCallCapSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(packet.entityId);
            if (entity instanceof LivingEntity) {
                entity.getCapability(NailCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                    cap.setEntityId(packet.entityId);
                    cap.setEntity((LivingEntity) entity);
                    cap.deserializeFromPacket(packet.isInGround, packet.isWallClimbing, packet.climbStartPos,
                            packet.nailCount, packet.nutCount,
                            packet.pinned, packet.pinPos, packet.pinFace, packet.struggleCount,
                            packet.pinStartTick, packet.pinOwnerUUID);
                });
            }
        }

        @Override
        public Class<NutKingCallCapSyncPacket> getPacketClass() {
            return NutKingCallCapSyncPacket.class;
        }
    }
}