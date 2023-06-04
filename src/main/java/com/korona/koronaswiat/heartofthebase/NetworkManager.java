//package com.korona.koronaswiat.heartofthebase;
//
//import com.korona.koronaswiat.KoronaSwiat;
//import fr.mosca421.worldprotector.api.event.RegionEvent;
//import fr.mosca421.worldprotector.core.IRegion;
//import fr.mosca421.worldprotector.data.DimensionRegionCache;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.util.RegistryKey;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.World;
//import net.minecraft.world.server.ServerWorld;
//import net.minecraft.world.storage.DimensionSavedDataManager;
//import net.minecraft.world.storage.WorldSavedData;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
//import net.minecraftforge.fml.server.ServerLifecycleHooks;
//
//import java.util.*;
//
//public class NetworkManager extends WorldSavedData {
//    public static final String TAG_REGIONS = "regions";
//    private static final String DATA_NAME = "koronaswiat";
//    private static final Map<RegistryKey<World>, DimensionRegionCache> heartMap = new HashMap();
//    private static NetworkManager clientHeartCopy = new NetworkManager();
//
//    private NetworkManager() {
//        super("koronaswiat");
//    }
//
//    public static NetworkManager get() {
//        if (clientHeartCopy == null) {
//            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
//            if (server != null) {
//                ServerWorld overworld = server.getLevel(World.OVERWORLD);
//                if (overworld != null && !overworld.isClientSide) {
//                    DimensionSavedDataManager storage = overworld.getDataStorage();
//                    clientHeartCopy = (NetworkManager)storage.computeIfAbsent(NetworkManager::new, "koronaswiat");
//                }
//            }
//        }
//
//        return clientHeartCopy;
//    }
//
//    public static void onServerStarting(FMLServerStartingEvent event) {
//        try {
//            ServerWorld world = (ServerWorld) Objects.requireNonNull(event.getServer().getLevel(World.OVERWORLD));
//            if (!world.isClientSide) {
//                DimensionSavedDataManager storage = world.getDataStorage();
//                NetworkManager data = (NetworkManager)storage.computeIfAbsent(NetworkManager::new, "koronaswiat");
//                storage.set(data);
//                clientHeartCopy = data;
//                KoronaSwiat.LOGGER.info("Loaded regions for dimensions");
//            }
//        } catch (NullPointerException var4) {
//            KoronaSwiat.LOGGER.error("Loading dimension regions failed");
//        }
//
//    }
//
//
//    @Override
//    public void load(CompoundNBT nbt) {
//        this.clearHearts();
//        CompoundNBT dimensionRegions = nbt.getCompound("heart_systems");
//        Iterator var3 = dimensionRegions.getAllKeys().iterator();
//
//        while(var3.hasNext()) {
//            String dimKey = (String)var3.next();
//            CompoundNBT dimRegionMap = dimensionRegions.getCompound(dimKey);
//            DimensionRegionCache dimCache = new DimensionRegionCache(dimRegionMap);
//            RegistryKey<World> dimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimKey));
//            heartMap.put(dimension, dimCache);
//        }
//
//    }
//
//    @Override
//    public CompoundNBT save(CompoundNBT compound) {
//        CompoundNBT dimHeartNbtData = new CompoundNBT();
//        Iterator var3 = heartMap.entrySet().iterator();
//
//        while(var3.hasNext()) {
//            Map.Entry<RegistryKey<World>, DimensionRegionCache> entry = (Map.Entry)var3.next();
//            String dim = ((RegistryKey)entry.getKey()).location().toString();
//            CompoundNBT dimCompound = ((DimensionRegionCache)entry.getValue()).serializeNBT();
//            dimHeartNbtData.put(dim, dimCompound);
//        }
//
//        compound.put("heart_systems", dimHeartNbtData);
//        return compound;
//    }
//
//    public Optional<DimensionRegionCache> getHeartsForDim(RegistryKey<World> dim) {
//        return heartMap.containsKey(dim) ? Optional.of(heartMap.get(dim)) : Optional.empty();
//    }
//
//    private Optional<RegistryKey<World>> getDimensionOfHeart(String heartName) {
//        return heartMap.entrySet().stream().filter((entry) -> {
//            return ((DimensionRegionCache)entry.getValue()).containsKey(heartName);
//        }).map(Map.Entry::getKey).findFirst();
//    }
//
//    public Optional<IRegion> getHeartInDim(RegistryKey<World> dim, String regionName) {
//        return heartMap.containsKey(dim) && ((DimensionRegionCache)heartMap.get(dim)).containsKey(regionName) ? Optional.of(((DimensionRegionCache)regionMap.get(dim)).get(regionName)) : Optional.empty();
//    }
//
//    public void addHeartToDim(IRegion region) {
//        RegistryKey<World> dim = region.getDimension();
//        if (heartMap.containsKey(dim)) {
//            ((DimensionRegionCache)heartMap.get(dim)).put(region.getName(), region);
//        } else {
//            DimensionRegionCache initMapForDim = new DimensionRegionCache(region);
//            heartMap.put(dim, initMapForDim);
//        }
//
//        this.setDirty();
//    }
//
//    public boolean removeHeart(String regionName, RegistryKey<World> dim) {
//        if (heartMap.containsKey(dim)) {
//            IRegion removed = (IRegion)((DimensionRegionCache)heartMap.get(dim)).remove(regionName);
//            this.setDirty();
//            return removed != null;
//        } else {
//            return false;
//        }
//    }
//
//    Optional<IRegion> maybeRegion = this.getHeart(heartName);
//        IRegion removed = ((DimensionRegionCache)heartMap.get(heart.getDimension())).removeHeart(heartName);
//        this.setDirty();
//        MinecraftForge.EVENT_BUS.post(new RegionEvent.RemoveRegionEvent(heart, (PlayerEntity)null));
//        return removed;
//    } else {
//        return null;
//    }
//}
//
//
//
//}