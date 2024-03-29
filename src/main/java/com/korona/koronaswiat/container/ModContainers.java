package com.korona.koronaswiat.container;

import com.korona.koronaswiat.KoronaSwiat;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
        = DeferredRegister.create(ForgeRegistries.CONTAINERS, KoronaSwiat.MOD_ID);

    public static final RegistryObject<ContainerType<WandContainer>> WAND_CONTAINER
        = CONTAINERS.register("wand_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new WandContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<AlchemicalFilterContainer>> ALCHEMICAL_FILTER_CONTAINER
            = CONTAINERS.register("alchemical_filter_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new AlchemicalFilterContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<UpgradeContainerContainer>> UPGRADE_CONTAINER_CONTAINER
            = CONTAINERS.register("upgrade_container_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new UpgradeContainerContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<HeartOfTheBaseContainer>> HEART_OF_THE_BASE_CONTAINER
            = CONTAINERS.register("heart_of_the_base_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new HeartOfTheBaseContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
