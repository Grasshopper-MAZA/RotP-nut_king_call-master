package com.grasshopper.nutkingcall.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraft.world.World;

public class NutKingCallToggleModeAction extends StandEntityAction {

    public NutKingCallToggleModeAction(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            return;
        }
        if (standEntity instanceof NutKingCallEntity) {
            ((NutKingCallEntity) standEntity).toggleDisassembleMode();
        }
    }

    @Override
    public String getTranslationKey(IStandPower power, ActionTarget target) {
        if (power.isActive() && power.getStandManifestation() instanceof NutKingCallEntity) {
            boolean disassemble = ((NutKingCallEntity) power.getStandManifestation()).isDisassembleMode();
            return "action.nutkingcall.nut_king_call_toggle_mode." + (disassemble ? "disassemble" : "assemble");
        }
        return super.getTranslationKey(power, target);
    }
}