package com.grasshopper.nutkingcall.action;

import java.util.Random;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.entity.NutKingCallNailEntity;
import com.grasshopper.nutkingcall.entity.NutKingCallNutEntity;

import net.minecraft.world.World;

public class NutKingCallFireNailAction extends StandEntityAction {

    private static final Random RANDOM = new Random();

    public NutKingCallFireNailAction(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) return;

        int count = 1 + RANDOM.nextInt(3);

        for (int i = 0; i < count; i++) {
            boolean isNut = RANDOM.nextBoolean();
            if (isNut) {
                NutKingCallNutEntity nut = new NutKingCallNutEntity(standEntity, world);
                standEntity.shootProjectile(nut, 1.5F, 1.0F + i * 3.0F);
            } else {
                NutKingCallNailEntity nail = new NutKingCallNailEntity(standEntity, world);
                standEntity.shootProjectile(nail, 1.5F, 1.0F + i * 3.0F);
            }
        }
    }
}