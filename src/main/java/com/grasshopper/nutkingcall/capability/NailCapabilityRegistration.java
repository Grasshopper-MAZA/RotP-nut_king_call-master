package com.grasshopper.nutkingcall.capability;

import com.grasshopper.nutkingcall.NutKingCallAddon;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NailCapabilityRegistration {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(NailCapability.class,
                new NailCapabilityStorage(),
                NailCapability::new);
    }
}