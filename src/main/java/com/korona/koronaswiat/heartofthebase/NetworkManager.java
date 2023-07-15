package com.korona.koronaswiat.heartofthebase;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.events.NetworkEvent;
import fr.mosca421.worldprotector.WorldProtector;
import fr.mosca421.worldprotector.core.IRegion;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkManager extends WorldSavedData {
    private static final Map<RegistryKey<World>, DimensionNetworkCache> networkMap = new HashMap();

    private static NetworkManager clientNetworkCopy = new NetworkManager();

    private NetworkManager() {
        super("koronaswiat");
    }

    public static NetworkManager get() {
        if (clientNetworkCopy == null) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                ServerWorld overworld = server.getLevel(World.OVERWORLD);
                if (overworld != null && !overworld.isClientSide) {
                    DimensionSavedDataManager storage = overworld.getDataStorage();
                    clientNetworkCopy = (NetworkManager) storage.computeIfAbsent(NetworkManager::new, "koronaswiat");
                }
            }
        }

        return clientNetworkCopy;
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        try {
            ServerWorld world = (ServerWorld)Objects.requireNonNull(event.getServer().getLevel(World.OVERWORLD));
            if (!world.isClientSide) {
                DimensionSavedDataManager storage = world.getDataStorage();
                NetworkManager data = (NetworkManager)storage.computeIfAbsent(NetworkManager::new, "koronaswiat");
                storage.set(data);
                clientNetworkCopy = data;
                KoronaSwiat.LOGGER.info("Loaded networks for different dimensions");
            }
        } catch (NullPointerException var4) {
            KoronaSwiat.LOGGER.error("Loading dimension networks failed");
        }

    }

    public void load(CompoundNBT nbt) {
        this.clearNetworks();
        CompoundNBT dimensionNetworks = nbt.getCompound("cosnets");
        Iterator var3 = dimensionNetworks.getAllKeys().iterator();

        while(var3.hasNext()) {
            String dimKey = (String)var3.next();
            CompoundNBT dimNetworkMap = dimensionNetworks.getCompound(dimKey);
            DimensionNetworkCache dimCache = new DimensionNetworkCache(dimNetworkMap);
            RegistryKey<World> dimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimKey));
            networkMap.put(dimension, dimCache);
        }
    }

    public CompoundNBT save(CompoundNBT compound) {
        CompoundNBT dimNetworkNbtData = new CompoundNBT();
        Iterator var3 = networkMap.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<RegistryKey<World>, DimensionNetworkCache> entry = (Map.Entry)var3.next();
            String dim = ((RegistryKey)entry.getKey()).location().toString();
            CompoundNBT dimCompound = ((DimensionNetworkCache)entry.getValue()).serializeNBT();
            dimNetworkNbtData.put(dim, dimCompound);
        }

        compound.put("cosnets", dimNetworkNbtData);
        return compound;
    }

    public Collection<INetwork> getAllNetworks() {
        return (Collection)networkMap.values().stream().flatMap((networkCache) -> {
            return networkCache.getNetworks().stream();
        }).collect(Collectors.toList());
    }

    public void addNetwork(INetwork network) {
        if (networkMap.containsKey(network.getDimension())) {
            ((DimensionNetworkCache)networkMap.get(network.getDimension())).addNetwork(network);
        } else {
            DimensionNetworkCache newCache = new DimensionNetworkCache(network);
            networkMap.put(network.getDimension(), newCache);
        }

        MinecraftForge.EVENT_BUS.post(new NetworkEvent.CreateNetworkEvent(network, (PlayerEntity)null));
        this.setDirty();
    }

    public INetwork removeNetwork(String networkName) {
        Optional<INetwork> maybeNetwork = this.getNetwork(networkName);
        if (maybeNetwork.isPresent()) {
            INetwork network = (INetwork)maybeNetwork.get();
            INetwork removed = ((DimensionNetworkCache)networkMap.get(network.getDimension())).removeNetwork(networkName);
            this.setDirty();
            MinecraftForge.EVENT_BUS.post(new NetworkEvent.RemoveNetworkEvent(network, (PlayerEntity)null));
            return removed;
        } else {
            return null;
        }
    }

    public Optional<INetwork> getNetwork(String networkName) {
        return networkMap.values().stream().filter((networkCache) -> {
            return networkCache.containsKey(networkName);
        }).map((networkCache) -> {
            return networkCache.getNetwork(networkName);
        }).findFirst();
    }

    public void clearNetworks() {
        networkMap.forEach((dim, cache) -> {
            cache.clearNetworks();
        });
        this.setDirty();
    }
}
