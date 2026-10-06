package com.grasshopper.nutkingcall;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grasshopper.nutkingcall.init.InitBlocks;
import com.grasshopper.nutkingcall.init.InitEntities;
import com.grasshopper.nutkingcall.init.InitSounds;
import com.grasshopper.nutkingcall.init.InitStands;
import com.grasshopper.nutkingcall.init.InitTileEntities;
import com.grasshopper.nutkingcall.network.NutKingCallPackets;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NutKingCallAddon.MOD_ID)
public class NutKingCallAddon {
    public static final String MOD_ID = "nutkingcall";
    public static final Logger LOGGER = LogManager.getLogger();

    public NutKingCallAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitBlocks.BLOCKS.register(modEventBus);
        InitTileEntities.TILE_ENTITIES.register(modEventBus);
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NutKingCallPackets::init);
    }
}