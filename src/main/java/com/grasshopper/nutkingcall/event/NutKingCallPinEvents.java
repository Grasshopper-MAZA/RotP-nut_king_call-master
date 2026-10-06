package com.grasshopper.nutkingcall.event;

import java.util.List;
import java.util.UUID;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.capability.NailCapability;
import com.grasshopper.nutkingcall.capability.NailCapabilityProvider;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID)
public class NutKingCallPinEvents {

    private static final int PIN_DURATION_TICKS = 600;
    private static final double UNPIN_DISTANCE_SQ = 100.0D;

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide()) return;

        NailCapability cap = entity.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap == null || !cap.isPinned()) return;

        entity.setDeltaMovement(0, 0, 0);
        entity.hurtMarked = true;
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 2, 100, false, false, false));

        boolean timeout = entity.tickCount - cap.getPinStartTick() > PIN_DURATION_TICKS;
        boolean farAway = false;

        UUID ownerUUID = cap.getPinOwnerUUID();
        if (ownerUUID != null) {
            LivingEntity owner = findEntityByUUID(entity.level, ownerUUID);
            if (owner == null || !owner.isAlive()) {
                farAway = true;
            } else if (owner.distanceToSqr(entity) > UNPIN_DISTANCE_SQ) {
                farAway = true;
            }
        }

        if (timeout || farAway) {
            NutKingCallAttachedNailEntity.unpin(entity.level, entity, true);
        }
    }

    private static LivingEntity findEntityByUUID(World world, UUID uuid) {
        if (world instanceof ServerWorld) {
            Entity e = ((ServerWorld) world).getEntity(uuid);
            if (e instanceof LivingEntity) {
                return (LivingEntity) e;
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player == null || player.level.isClientSide()) return;

        World world = player.level;
        BlockPos pos = event.getPos();

        List<NutKingCallAttachedNailEntity> nails = world.getEntitiesOfClass(
                NutKingCallAttachedNailEntity.class,
                new AxisAlignedBB(pos).inflate(1.0D));

        for (NutKingCallAttachedNailEntity nail : nails) {
            if (nail.isPinned() && pos.equals(nail.getPinnedBlockPos())) {
                event.setCanceled(true);
                return;
            }
        }
    }
}