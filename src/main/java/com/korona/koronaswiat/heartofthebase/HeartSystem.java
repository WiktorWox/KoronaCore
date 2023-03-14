package com.korona.koronaswiat.heartofthebase;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Set;

public class HeartSystem implements IHeartSystem {
    private String name;
    private RegistryKey<World> dimension;
    public String ownerName;
    private Set<BlockPos> blocksPos;
    private Set<String> blocks;

    public HeartSystem(String name, RegistryKey<World> dimension, PlayerEntity owner) {
        this.name = name;
        this.dimension = dimension;
        this.ownerName = owner.toString();
    }

    public void addBlock(BlockPos pos, Block block) {
        blocksPos.add(pos);
        blocks.add(Registry.BLOCK.getKey(block).toString());
    }

    public boolean isBlock(Block block) {
        return blocks.contains(Registry.BLOCK.getKey(block).toString());
    }

    public BlockPos getBlockPos(Block block) {
        if (blocksPos.contains(Registry.BLOCK.getKey(block).toString())) {
            Set<BlockPos> matchingBlocksPos = null;
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.toArray()[i].equals(Registry.BLOCK.getKey(block).toString())) {
                    BlockPos pos = (BlockPos) blocksPos.toArray()[i];
                    matchingBlocksPos.add(pos);
                }
            }
            if (matchingBlocksPos.size() == 0) {
                return (BlockPos) matchingBlocksPos.toArray()[0];
            } else {
                int random = (int) (Math.random() * matchingBlocksPos.size());
                return (BlockPos) matchingBlocksPos.toArray()[random];
            }
        } else {
            throw new IllegalArgumentException("No blocks of this type in this heart system");
        }
    }

    public void removeBlock(BlockPos pos) {
        if (blocksPos.contains(pos)) {
            for (int i = 0; i < blocksPos.size(); i++) {
                if (blocksPos.toArray()[i].equals(pos)) {
                    blocksPos.remove(pos);
                    blocks.remove(blocks.toArray()[i]);
                }
            }
        } else {
            throw new IllegalArgumentException("Block not found");
        }
    }

    public void changeOwner(PlayerEntity newOwner) {
        this.ownerName = newOwner.getName().getString();
    }

    @Override
    public CompoundNBT serializeNBT() {
        //We saving all data in one CompoundNBT
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name", name);
        nbt.putString("owner", ownerName);
        nbt.putString("dimension", dimension.location().toString());
        ListNBT blocksPosNBT = new ListNBT();
        blocksPos.forEach(pos -> blocksPosNBT.add(StringNBT.valueOf(pos.toString())));
        nbt.put("blocks_pos", blocksPosNBT);
        ListNBT blocksNBT = new ListNBT();
        blocks.forEach(block -> blocksNBT.add(StringNBT.valueOf(block)));
        nbt.put("blocks", blocksNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        //We loading all data from one CompoundNBT
        this.name = nbt.getString("name");
        this.ownerName = nbt.getString("owner");
        this.dimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(nbt.getString("dimension")));
        ListNBT blocksPosNBT = nbt.getList("blocks_pos", 8);
        blocksPosNBT.forEach(pos -> blocksPos.add(new BlockPos(Integer.valueOf(pos.toString().split(",")[0]), Integer.valueOf(pos.toString().split(",")[1]), Integer.valueOf(pos.toString().split(",")[2]))));
        ListNBT blocksList = nbt.getList("blocks", 8);
        for(int i = 0; i < blocksList.size(); ++i) {
            this.blocks.add(blocksList.getString(i));
        }
    }
}
