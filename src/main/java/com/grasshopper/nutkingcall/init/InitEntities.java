package com.grasshopper.nutkingcall.init;

import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.entity.NutKingCallAttachedNailEntity;
import com.grasshopper.nutkingcall.entity.NutKingCallNailEntity;
import com.grasshopper.nutkingcall.entity.NutKingCallNutEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = NutKingCallAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, NutKingCallAddon.MOD_ID);

    public static final RegistryObject<EntityType<NutKingCallNailEntity>> NUT_KING_CALL_NAIL = ENTITIES.register("nut_king_call_nail",
            () -> EntityType.Builder.<NutKingCallNailEntity>of(NutKingCallNailEntity::new, EntityClassification.MISC)
                    .sized(0.3F, 0.3F)
                    .build(NutKingCallAddon.MOD_ID + ":nut_king_call_nail"));

    public static final RegistryObject<EntityType<NutKingCallNutEntity>> NUT_KING_CALL_NUT = ENTITIES.register("nut_king_call_nut",
            () -> EntityType.Builder.<NutKingCallNutEntity>of(NutKingCallNutEntity::new, EntityClassification.MISC)
                    .sized(0.3F, 0.3F)
                    .build(NutKingCallAddon.MOD_ID + ":nut_king_call_nut"));

    public static final RegistryObject<EntityType<NutKingCallAttachedNailEntity>> NUT_KING_CALL_ATTACHED_NAIL = ENTITIES.register("nut_king_call_attached_nail",
            () -> EntityType.Builder.<NutKingCallAttachedNailEntity>of(NutKingCallAttachedNailEntity::new, EntityClassification.MISC)
                    .sized(0.3F, 0.3F)
                    .build(NutKingCallAddon.MOD_ID + ":nut_king_call_attached_nail"));

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
    }
}