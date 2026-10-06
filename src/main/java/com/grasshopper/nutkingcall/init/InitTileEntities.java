package com.grasshopper.nutkingcall.init;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.tileentity.NutKingCallNailTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, NutKingCallAddon.MOD_ID);

    public static final RegistryObject<TileEntityType<NutKingCallNailTileEntity>> NUT_KING_CALL_NAIL = TILE_ENTITIES.register("nut_king_call_nail",
            () -> TileEntityType.Builder.of(NutKingCallNailTileEntity::new,
                    InitBlocks.NUT_KING_CALL_NAIL_BLOCK.get()).build(null));
}