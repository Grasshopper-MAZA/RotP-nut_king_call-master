package com.grasshopper.nutkingcall.client;

import com.grasshopper.nutkingcall.NutKingCallAddon;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID, value = Dist.CLIENT)
public class NailClientEvents {
}