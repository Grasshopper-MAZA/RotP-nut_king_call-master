package com.grasshopper.nutkingcall.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class NutKingCallEntity extends StandEntity {

    private static final DataParameter<Boolean> DISASSEMBLE_MODE =
            EntityDataManager.defineId(NutKingCallEntity.class, DataSerializers.BOOLEAN);

    public NutKingCallEntity(StandEntityType<NutKingCallEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DISASSEMBLE_MODE, false);
    }

    public boolean isDisassembleMode() {
        return entityData.get(DISASSEMBLE_MODE);
    }

    public void setDisassembleMode(boolean disassemble) {
        entityData.set(DISASSEMBLE_MODE, disassemble);
    }

    public void toggleDisassembleMode() {
        entityData.set(DISASSEMBLE_MODE, !entityData.get(DISASSEMBLE_MODE));
    }
}