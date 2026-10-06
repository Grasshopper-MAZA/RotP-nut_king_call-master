package com.grasshopper.nutkingcall.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.action.NutKingCallDismantleAction;
import com.grasshopper.nutkingcall.action.NutKingCallFireNailAction;
import com.grasshopper.nutkingcall.action.NutKingCallFlyingKickAction;
import com.grasshopper.nutkingcall.action.NutKingCallGiveNailAction;
import com.grasshopper.nutkingcall.action.NutKingCallPinAction;
import com.grasshopper.nutkingcall.action.NutKingCallPinSelfAction;
import com.grasshopper.nutkingcall.action.NutKingCallQuickAssemblyAction;
import com.grasshopper.nutkingcall.action.NutKingCallToggleModeAction;
import com.grasshopper.nutkingcall.action.NutKingCallUnpinSelfAction;
import com.grasshopper.nutkingcall.action.NutKingCallUnpinTargetAction;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), NutKingCallAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), NutKingCallAddon.MOD_ID);

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_PUNCH = ACTIONS.register("nut_king_call_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.NUT_KING_CALL_PUNCH_LIGHT)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_BARRAGE = ACTIONS.register("nut_king_call_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.NUT_KING_CALL_PUNCH_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> NUT_KING_CALL_HEAVY_PUNCH = ACTIONS.register("nut_king_call_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .shiftVariationOf(NUT_KING_CALL_PUNCH)
                    .shiftVariationOf(NUT_KING_CALL_BARRAGE)
                    .punchSound(InitSounds.NUT_KING_CALL_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_BLOCK = ACTIONS.register("nut_king_call_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_QUICK_ASSEMBLY = ACTIONS.register("nut_king_call_quick_assembly",
            () -> new NutKingCallQuickAssemblyAction(new StandEntityAction.Builder()
                    .standPerformDuration(20)
                    .standRecoveryTicks(20)
                    .staminaCost(50)
                    .cooldown(200, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_TOGGLE_MODE = ACTIONS.register("nut_king_call_toggle_mode",
            () -> new NutKingCallToggleModeAction(new StandEntityAction.Builder()
                    .shiftVariationOf(NUT_KING_CALL_QUICK_ASSEMBLY)
                    .standPerformDuration(5)
                    .standRecoveryTicks(5)
                    .staminaCost(10)
                    .cooldown(20, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_FIRE_NAIL = ACTIONS.register("nut_king_call_fire_nail",
            () -> new NutKingCallFireNailAction(new StandEntityAction.Builder()
                    .standPerformDuration(10)
                    .standRecoveryTicks(10)
                    .staminaCost(25)
                    .cooldown(20, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_DISMANTLE = ACTIONS.register("nut_king_call_dismantle",
            () -> new NutKingCallDismantleAction(new StandEntityAction.Builder()
                    .shiftVariationOf(NUT_KING_CALL_FIRE_NAIL)
                    .standPerformDuration(20)
                    .standRecoveryTicks(20)
                    .staminaCost(30)
                    .cooldown(60, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_GIVE_NAIL = ACTIONS.register("nut_king_call_give_nail",
            () -> new NutKingCallGiveNailAction(new StandEntityAction.Builder()
                    .standPerformDuration(10)
                    .standRecoveryTicks(10)
                    .staminaCost(20)
                    .cooldown(40, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_PIN = ACTIONS.register("nut_king_call_pin",
            () -> new NutKingCallPinAction(new StandEntityAction.Builder()
                    .standPerformDuration(10)
                    .standRecoveryTicks(10)
                    .staminaCost(30)
                    .cooldown(40, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_UNPIN_TARGET = ACTIONS.register("nut_king_call_unpin_target",
            () -> new NutKingCallUnpinTargetAction(new StandEntityAction.Builder()
                    .shiftVariationOf(NUT_KING_CALL_PIN)
                    .standPerformDuration(5)
                    .standRecoveryTicks(5)
                    .staminaCost(10)
                    .cooldown(20, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_PIN_SELF = ACTIONS.register("nut_king_call_pin_self",
            () -> new NutKingCallPinSelfAction(new StandEntityAction.Builder()
                    .standPerformDuration(10)
                    .standRecoveryTicks(10)
                    .staminaCost(30)
                    .cooldown(40, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_UNPIN_SELF = ACTIONS.register("nut_king_call_unpin_self",
            () -> new NutKingCallUnpinSelfAction(new StandEntityAction.Builder()
                    .shiftVariationOf(NUT_KING_CALL_PIN_SELF)
                    .standPerformDuration(5)
                    .standRecoveryTicks(5)
                    .staminaCost(10)
                    .cooldown(20, 0)));

    public static final RegistryObject<StandEntityAction> NUT_KING_CALL_FLYING_KICK = ACTIONS.register("nut_king_call_flying_kick",
            () -> new NutKingCallFlyingKickAction(new StandEntityAction.Builder()
                    .standPerformDuration(3)
                    .standRecoveryTicks(5)
                    .staminaCost(40)
                    .cooldown(100, 0)));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<NutKingCallEntity>> STAND_NUT_KING_CALL =
            new EntityStandRegistryObject<>("nut_king_call",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                            .color(0x8A2BE2)
                            .storyPartName(ModStandsInit.PART_8_NAME)
                            .leftClickHotbar(
                                    NUT_KING_CALL_PUNCH.get(),
                                    NUT_KING_CALL_BARRAGE.get(),
                                    NUT_KING_CALL_FIRE_NAIL.get(),
                                    NUT_KING_CALL_FLYING_KICK.get()
                            )
                            .rightClickHotbar(
                                    NUT_KING_CALL_BLOCK.get(),
                                    NUT_KING_CALL_QUICK_ASSEMBLY.get(),
                                    NUT_KING_CALL_GIVE_NAIL.get(),
                                    NUT_KING_CALL_PIN.get(),
                                    NUT_KING_CALL_PIN_SELF.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(6)
                                    .power(10)
                                    .speed(5)
                                    .range(5, 10)
                                    .durability(17)
                                    .precision(1)
                                    .build())
                            .addSummonShout(InitSounds.NUT_KING_CALL_SUMMON_VOICELINE)
                            .addOst(InitSounds.NUT_KING_CALL_OST)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<NutKingCallEntity>(NutKingCallEntity::new, 0.7F, 2.1F)
                            .summonSound(InitSounds.NUT_KING_CALL_SUMMON_SOUND)
                            .unsummonSound(InitSounds.NUT_KING_CALL_UNSUMMON_SOUND))
                    .withDefaultStandAttributes();
}