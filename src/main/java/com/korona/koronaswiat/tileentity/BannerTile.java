package com.korona.koronaswiat.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BannerTile extends TileEntity {

    public BannerTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public BannerTile() {
        this(ModTileEntities.BANNER_TILE.get());
    }
}
