package com.grasshopper.nutkingcall.capability;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID)
public class NailClimbEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        PlayerEntity player = event.player;
        NailCapability cap = player.getCapability(NailCapabilityProvider.CAPABILITY).orElse(null);
        if (cap == null || !cap.isWallClimbing()) {
            return;
        }

        BlockPos pos = cap.getClimbStartPos();
        if (!NutKingCallNailBlock.isClimbable(player.level, pos)) {
            cap.setWallClimbing(false);
            return;
        }

        player.fallDistance = 0;

        if (player.isShiftKeyDown()) {
            player.setDeltaMovement(player.getDeltaMovement().add(0, -0.1, 0));
        } else if (player.zza > 0) {
            player.setDeltaMovement(player.getDeltaMovement().add(0, 0.1, 0));
        } else if (player.zza < 0) {
            player.setDeltaMovement(player.getDeltaMovement().add(0, -0.1, 0));
        } else {
            Vector3d motion = player.getDeltaMovement();
            player.setDeltaMovement(0, motion.y * 0.5, 0);
        }

        player.hurtMarked = true;
    }
}