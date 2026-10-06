package com.grasshopper.nutkingcall.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class NailCapabilityStorage implements Capability.IStorage<NailCapability> {

    @Override
    public INBT writeNBT(Capability<NailCapability> capability, NailCapability instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<NailCapability> capability, NailCapability instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}