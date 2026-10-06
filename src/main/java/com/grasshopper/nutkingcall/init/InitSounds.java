package com.grasshopper.nutkingcall.init;

import java.util.function.Supplier;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import com.grasshopper.nutkingcall.NutKingCallAddon;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, NutKingCallAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> NUT_KING_CALL_SUMMON_VOICELINE = SOUNDS.register("nut_king_call_summon_voiceline",
            () -> new SoundEvent(new ResourceLocation(NutKingCallAddon.MOD_ID, "nut_king_call_summon_voiceline")));

    public static final Supplier<SoundEvent> NUT_KING_CALL_SUMMON_SOUND = ModSounds.STAND_SUMMON_DEFAULT;
    public static final Supplier<SoundEvent> NUT_KING_CALL_UNSUMMON_SOUND = ModSounds.STAND_UNSUMMON_DEFAULT;
    public static final Supplier<SoundEvent> NUT_KING_CALL_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    public static final Supplier<SoundEvent> NUT_KING_CALL_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    public static final Supplier<SoundEvent> NUT_KING_CALL_PUNCH_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;

    public static final OstSoundList NUT_KING_CALL_OST = new OstSoundList(
            new ResourceLocation(NutKingCallAddon.MOD_ID, "nut_king_call_ost"), SOUNDS);
}