package com.grasshopper.nutkingcall.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public class NailStorage extends Inventory implements INamedContainerProvider {

    private final UUID mobUUID;
    private final ITextComponent name;

    public NailStorage(ITextComponent name, UUID mobUUID) {
        super(9);
        this.name = name;
        this.mobUUID = mobUUID;
    }

    public UUID getMobUUID() { return mobUUID; }

    @Override
    public ITextComponent getDisplayName() { return name; }

    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
        return null;
    }

    public ListNBT createTag() {
        ListNBT listnbt = new ListNBT();
        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putByte("Slot", (byte) i);
                itemstack.save(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }
        return listnbt;
    }

    public void fromTag(ListNBT nbt) {
        for (int i = 0; i < this.getContainerSize(); ++i) this.setItem(i, ItemStack.EMPTY);
        for (int k = 0; k < nbt.size(); ++k) {
            CompoundNBT compoundnbt = nbt.getCompound(k);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j >= 0 && j < this.getContainerSize()) this.setItem(j, ItemStack.of(compoundnbt));
        }
    }
}