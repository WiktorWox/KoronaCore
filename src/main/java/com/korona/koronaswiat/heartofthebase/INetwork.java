package com.korona.koronaswiat.heartofthebase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface INetwork extends INBTSerializable<CompoundNBT> {
    String getName();

    RegistryKey<World> getDimension();

    void addBlock(BlockPos pos);

    void removeBlock(BlockPos pos);

    boolean isBlock(BlockPos pos);
}
