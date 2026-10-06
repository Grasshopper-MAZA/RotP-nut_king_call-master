package com.grasshopper.nutkingcall.capability;

import com.grasshopper.nutkingcall.NutKingCallAddon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID)
public class NailCapabilityAttach {

    private static final ResourceLocation NAIL_CAP =
            new ResourceLocation(NutKingCallAddon.MOD_ID, "nail_capability");

    @SubscribeEvent
    public static void onAttachCapabilitiesToEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            event.addCapability(NAIL_CAP, new NailCapabilityProvider((LivingEntity) entity));
        }
    }
}