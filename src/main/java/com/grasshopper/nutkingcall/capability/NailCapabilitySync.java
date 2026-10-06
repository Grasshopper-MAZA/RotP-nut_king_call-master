package com.grasshopper.nutkingcall.capability;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.network.NutKingCallCapSyncPacket;
import com.grasshopper.nutkingcall.network.NutKingCallPackets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NailCapabilitySync {

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        Entity target = event.getTarget();
        if (target instanceof LivingEntity) {
            target.getCapability(NailCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                if (event.getPlayer() instanceof ServerPlayerEntity) {
                    NutKingCallPackets.sendToClient(new NutKingCallCapSyncPacket(cap), (ServerPlayerEntity) event.getPlayer());
                }
            });
        }
    }
}