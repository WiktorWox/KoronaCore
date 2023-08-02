package com.korona.koronaswiat.heartofthebase;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Network implements INetwork {
    private String name;

    private final Set<BlockPos> positions = new HashSet<>();

    private RegistryKey<World> dimension;

    public Network(CompoundNBT nbt) {
        this.deserializeNBT(nbt);
    }

    public Network(String name, RegistryKey<World> dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    public String getName() {
        return this.name;
    }

    public RegistryKey<World> getDimension() {
        return this.dimension;
    }

    public void addBlock(BlockPos pos) {
        positions.add(pos);
    }

    public void removeBlock(BlockPos pos) {
        positions.remove(pos);
    }

    public boolean isBlock(BlockPos pos) {
        return positions.contains(pos);
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name", this.name);
        nbt.putString("dimension", this.dimension.location().toString());
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        this.name = nbt.getString("name");
        this.dimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(nbt.getString("dimension")));
    }
}
