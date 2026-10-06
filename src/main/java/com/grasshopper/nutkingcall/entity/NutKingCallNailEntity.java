package com.grasshopper.nutkingcall.entity;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;
import com.grasshopper.nutkingcall.init.InitEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class NutKingCallNailEntity extends ModdedProjectileEntity {

    public NutKingCallNailEntity(EntityType<NutKingCallNailEntity> type, World world) {
        super(type, world);
    }

    public NutKingCallNailEntity(LivingEntity shooter, World world) {
        super(InitEntities.NUT_KING_CALL_NAIL.get(), shooter, world);
    }

    @Override
    protected float getBaseDamage() { return 5.0F; }

    @Override
    protected float getMaxHardnessBreakable() { return 0.0F; }

    @Override
    public boolean standDamage() { return true; }

    @Override
    public int ticksLifespan() { return 200; }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        if (!level.isClientSide()) {
            BlockPos hitPos = blockRayTraceResult.getBlockPos();
            Direction face = blockRayTraceResult.getDirection();
            NutKingCallNailBlock.placeNail(level, hitPos, face);
            remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        if (level.isClientSide()) {
            remove();
            return;
        }

        Entity hitEntity = entityRayTraceResult.getEntity();
        if (!(hitEntity instanceof LivingEntity)) {
            super.onHitEntity(entityRayTraceResult);
            remove();
            return;
        }

        LivingEntity target = (LivingEntity) hitEntity;

        boolean armorBroken = damageArmorFirst(target, getBaseDamage());

        if (!armorBroken) {
            super.onHitEntity(entityRayTraceResult);
        }

        NutKingCallAttachedNailEntity.attachNail(level, target);
        remove();
    }

    private boolean damageArmorFirst(LivingEntity target, float damage) {
        ItemStack chest = target.getItemBySlot(EquipmentSlotType.CHEST);
        if (chest.isEmpty()) {
            return false;
        }
        if (!chest.isDamageableItem()) {
            return false;
        }

        int durabilityDamage = Math.max(1, (int) damage);
        int newDamage = chest.getDamageValue() + durabilityDamage;

        if (newDamage >= chest.getMaxDamage()) {
            chest.setDamageValue(chest.getMaxDamage() - 1);
            target.broadcastBreakEvent(EquipmentSlotType.CHEST);
            target.setItemSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
            return false;
        }

        chest.setDamageValue(newDamage);
        return true;
    }
}