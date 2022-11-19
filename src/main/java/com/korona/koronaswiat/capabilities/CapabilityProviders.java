//package com.korona.koronaswiat.capabilities;
//
//import com.korona.koronaswiat.KoronaSwiat;
//import com.korona.koronaswiat.item.ModItems;
//import com.korona.koronaswiat.tileentity.WandTile;
//import net.minecraft.block.Blocks;
//import net.minecraft.nbt.INBT;
//import net.minecraft.tileentity.TileEntityType;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.fml.RegistryObject;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class CapabilityProviders {
//
//    public static DeferredRegister<ICapabilityProvider> TILE_ENTITIES =
//            DeferredRegister.create(ForgeRegistries.CAP, KoronaSwiat.MOD_ID);
//
//    public static RegistryObject<TileEntityType<WandTile>> WAND_CAPABILITY_PROVIDER =
//            TILE_ENTITIES.register("wand_capability_provider", () -> TileEntityType.Builder.of(
//                    WandCapabilityProvider::new, ModItems.WAND).build(null));
//
//    public static void register(IEventBus eventBus) {
//        TILE_ENTITIES.register(eventBus);
//    }
//
//}
