package com.grasshopper.nutkingcall.tileentity;

import java.util.List;

import com.grasshopper.nutkingcall.block.NutKingCallNailBlock;
import com.grasshopper.nutkingcall.init.InitTileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NutKingCallNailTileEntity extends TileEntity implements ITickableTileEntity {

    private static final int CHECK_DISTANCE = 10;

    public NutKingCallNailTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public NutKingCallNailTileEntity() {
        this(InitTileEntities.NUT_KING_CALL_NAIL.get());
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }

        if (this.level.getGameTime() % 20 != 0) {
            return;
        }

        BlockState state = this.getBlockState();
        if (!(state.getBlock() instanceof NutKingCallNailBlock)) {
            return;
        }

        if (!hasNutKingCallUserNearby()) {
            this.level.removeBlock(this.worldPosition, false);
        }
    }

    private boolean hasNutKingCallUserNearby() {
        World world = this.level;
        BlockPos pos = this.worldPosition;
        AxisAlignedBB area = new AxisAlignedBB(pos).inflate(CHECK_DISTANCE);
        List<PlayerEntity> players = world.getEntitiesOfClass(PlayerEntity.class, area);
        for (PlayerEntity player : players) {
            if (isNutKingCallUser(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNutKingCallUser(PlayerEntity player) {
        try {
            com.github.standobyte.jojo.power.impl.stand.IStandPower power =
                    com.github.standobyte.jojo.power.impl.stand.IStandPower.getPlayerStandPower(player);
            if (power == null || power.getType() == null) {
                return false;
            }
            String standName = power.getType().getRegistryName() != null
                    ? power.getType().getRegistryName().toString()
                    : "";
            return standName.contains("nut_king_call");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
    }
}