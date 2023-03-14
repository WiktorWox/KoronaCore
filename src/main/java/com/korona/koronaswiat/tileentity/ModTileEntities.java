package com.korona.koronaswiat.tileentity;

import com.korona.koronaswiat.KoronaSwiat;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, KoronaSwiat.MOD_ID);

    public static RegistryObject<TileEntityType<BannerTile>> BANNER_TILE =
            TILE_ENTITIES.register("banner_tile", () -> TileEntityType.Builder.of(
                    BannerTile::new, Blocks.COBBLESTONE).build(null));

    public static RegistryObject<TileEntityType<AlchemicalFilterTile>> ALCHEMICAL_FILTER_TILE =
            TILE_ENTITIES.register("alchemical_filter_tile", () -> TileEntityType.Builder.of(
                    AlchemicalFilterTile::new, Blocks.COBBLESTONE).build(null));

    public static RegistryObject<TileEntityType<UpgradeContainerTile>> UPGRADE_CONTAINER_TILE =
            TILE_ENTITIES.register("upgrade_container_tile", () -> TileEntityType.Builder.of(
                    UpgradeContainerTile::new, Blocks.COBBLESTONE).build(null));

    public static RegistryObject<TileEntityType<HeartOfTheBaseTile>> HEART_OF_THE_BASE_TILE =
            TILE_ENTITIES.register("heart_of_the_base_tile", () -> TileEntityType.Builder.of(
                    HeartOfTheBaseTile::new, Blocks.COBBLESTONE).build(null));

    public static RegistryObject<TileEntityType<DiscTile>> DISC_TILE =
            TILE_ENTITIES.register("disc_tile", () -> TileEntityType.Builder.of(
                    DiscTile::new, Blocks.COBBLESTONE).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
