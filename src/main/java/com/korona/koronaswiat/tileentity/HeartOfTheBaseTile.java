package com.korona.koronaswiat.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class HeartOfTheBaseTile extends TileEntity {

    public HeartOfTheBaseTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public HeartOfTheBaseTile() {
        this(ModTileEntities.HEART_OF_THE_BASE_TILE.get());
    }
}
