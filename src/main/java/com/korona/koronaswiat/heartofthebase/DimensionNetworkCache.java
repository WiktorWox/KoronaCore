package com.korona.koronaswiat.heartofthebase;

import fr.mosca421.worldprotector.core.IRegion;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class DimensionNetworkCache extends HashMap<String, INetwork> implements INBTSerializable<CompoundNBT> {
    public static final String NETWORKS = "networks";

    public DimensionNetworkCache(INetwork network) {
        this();
        this.addNetwork(network);
    }

    public DimensionNetworkCache() {
//        this.dimensionFlags = new ArrayList(0);
//        this.protectors = new ArrayList(0);
//        this.hasWhitelist = true;
    }

    public DimensionNetworkCache(CompoundNBT nbt) {
        this();
        this.deserializeNBT(nbt);
    }

    public void addNetwork(INetwork network) {
        this.put(network.getName(), network);
    }

    public INetwork removeNetwork(String networkName) {
        return (INetwork)this.remove(networkName);
    }

    public void clearNetworks() {
        this.clear();
    }

    public Collection<INetwork> getNetworks() {
        return Collections.unmodifiableCollection(this.values());
    }

    public INetwork getNetwork(String networkName) {
        return (INetwork)this.get(networkName);
    }

    public static CompoundNBT serializeCache(DimensionNetworkCache dimensionNetworkCache) {
        CompoundNBT dimCache = new CompoundNBT();
        Iterator var2 = dimensionNetworkCache.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, INetwork> networkEntry = (Map.Entry)var2.next();
            dimCache.put((String)networkEntry.getKey(), ((INetwork)networkEntry.getValue()).serializeNBT());
        }

        return dimCache;
    }

    public static DimensionNetworkCache deserialize(CompoundNBT nbt) {
        DimensionNetworkCache dimCache = new DimensionNetworkCache();
        Iterator var2 = nbt.getAllKeys().iterator();

        while(var2.hasNext()) {
            String networkKey = (String)var2.next();
            CompoundNBT networkNbt = nbt.getCompound(networkKey);
            Network network = new Network(networkNbt);
            dimCache.addNetwork(network);
        }

        return dimCache;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT networks = new CompoundNBT();
        Iterator var3 = this.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, INetwork> networkEntry = (Map.Entry)var3.next();
            networks.put((String)networkEntry.getKey(), ((INetwork)networkEntry.getValue()).serializeNBT());
        }

        nbt.put("networks", networks);
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        CompoundNBT networks = nbt.getCompound("networks");
        Iterator var3 = networks.getAllKeys().iterator();

        while(var3.hasNext()) {
            String networkKey = (String)var3.next();
            CompoundNBT networkNbt = networks.getCompound(networkKey);
            Network network = new Network(networkNbt);
            this.addNetwork(network);
        }

//        this.dimensionFlags.clear();
//        ListNBT flagsNBT = nbt.getList("flags", 8);
//
//        for(int i = 0; i < flagsNBT.size(); ++i) {
//            this.dimensionFlags.add(flagsNBT.getString(i));
//        }
//
//        this.protectors.clear();
//        ListNBT protectorsNBT = nbt.getList("protectors", 8);
//
//        for(int i = 0; i < protectorsNBT.size(); ++i) {
//            this.dimensionFlags.add(protectorsNBT.getString(i));
//        }
//
//        this.hasWhitelist = nbt.getBoolean("whitelist");
    }
}
