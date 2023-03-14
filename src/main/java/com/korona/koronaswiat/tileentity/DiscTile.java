package com.korona.koronaswiat.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class DiscTile extends TileEntity {
    public DiscTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public DiscTile() {
        this(ModTileEntities.DISC_TILE.get());
    }
}
