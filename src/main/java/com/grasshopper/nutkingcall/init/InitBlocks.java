package com.grasshopper.nutkingcall.init;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, NutKingCallAddon.MOD_ID);

    public static final RegistryObject<Block> NUT_KING_CALL_NAIL_BLOCK = BLOCKS.register("nut_king_call_nail_block",
            NutKingCallNailBlock::new);
}