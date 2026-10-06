package com.grasshopper.nutkingcall.client;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.client.render.NutKingCallNailBlockRenderer;
import com.grasshopper.nutkingcall.client.render.NutKingCallNailRenderer;
import com.grasshopper.nutkingcall.client.render.NutKingCallNutRenderer;
import com.grasshopper.nutkingcall.client.render.NutKingCallRenderer;
import com.grasshopper.nutkingcall.init.InitBlocks;
import com.grasshopper.nutkingcall.init.InitEntities;
import com.grasshopper.nutkingcall.init.InitStands;
import com.grasshopper.nutkingcall.init.InitTileEntities;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = NutKingCallAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NutKingCallClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_NUT_KING_CALL.getEntityType(),
                NutKingCallRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.NUT_KING_CALL_NAIL.get(),
                manager -> new NutKingCallNailRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.NUT_KING_CALL_NUT.get(),
                manager -> new NutKingCallNutRenderer(manager));


        ClientRegistry.bindTileEntityRenderer(
                InitTileEntities.NUT_KING_CALL_NAIL.get(),
                NutKingCallNailBlockRenderer::new);

        RenderTypeLookup.setRenderLayer(
                InitBlocks.NUT_KING_CALL_NAIL_BLOCK.get(),
                RenderType.cutout());
    }
}