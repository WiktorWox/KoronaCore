package com.korona.koronaswiat.heartofthebase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeartSystem extends INBTSerializable<CompoundNBT> {
    void addBlock(BlockPos pos, Block block);
    void removeBlock(BlockPos pos);
    void changeOwner(PlayerEntity newOwner);
    boolean isBlock(Block block);
    BlockPos getBlockPos(Block block);
}
