package com.grasshopper.nutkingcall.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class NailCapabilityProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(NailCapability.class)
    public static Capability<NailCapability> CAPABILITY = null;

    private final LazyOptional<NailCapability> instance;

    public NailCapabilityProvider(LivingEntity entity) {
        this.instance = LazyOptional.of(() -> new NailCapability(entity));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public INBT serializeNBT() {
        return CAPABILITY.getStorage().writeNBT(CAPABILITY, instance.orElseThrow(
                () -> new IllegalArgumentException("NailCapability LazyOptional is not attached.")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance.orElseThrow(
                () -> new IllegalArgumentException("NailCapability LazyOptional is not attached.")), null, nbt);
    }
}