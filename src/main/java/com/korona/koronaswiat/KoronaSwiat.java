package com.korona.koronaswiat;

import com.korona.koronaswiat.block.ModBlocks;
import com.korona.koronaswiat.container.ModContainers;
import com.korona.koronaswiat.entity.Villager;
import com.korona.koronaswiat.guis.WandGui;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.screen.AlchemicalFilterScreen;
import com.korona.koronaswiat.screen.HeartOfTheBaseScreen;
import com.korona.koronaswiat.screen.UpgradeContainerScreen;
import com.korona.koronaswiat.screen.WandScreen;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(KoronaSwiat.MOD_ID)
public class KoronaSwiat {
    // Directly reference a log4j logger.
//    public static final RegistryObject<Item> GLASS_BOTTLE = RegistryObject.of(new ResourceLocation("minecraft:potion"), ForgeRegistries.ITEMS);

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "koronaswiat";
    public KoronaSwiat() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTileEntities.register(eventBus);
        ModSoundEvent.register(eventBus);
        ModContainers.register((eventBus));
        Villager.VILLAGER_PROFESSION.register(eventBus);
        Villager.POINT_OF_INTEREST_TYPE.register(eventBus);
        MinecraftForge.EVENT_BUS.register(new WandGui());

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        System.out.println("test");
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        LOGGER.info("=====  .-===-.  .-===-.                         ||          ||  ");
        LOGGER.info("  |    |     |  |     '                         ||        ''||''");
        LOGGER.info("  |    |     |  '-===-.  ||   ||  |.:::.   .:::.||  .:::.|| ||    .:::.");
        LOGGER.info("  |    |     |        |  ||   ||  ||   || ||   ||| ||   ||| ||   ||---'");
        LOGGER.info("=====  '-===-'  '-===-'  '|:::|'| ||:::'   ':::'||  ':::'||  '::  ':::'");
        LOGGER.info("                                  ||");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        event.enqueueWork(() -> {
            ScreenManager.register(ModContainers.WAND_CONTAINER.get(),
                    WandScreen::new);
            ScreenManager.register(ModContainers.HEART_OF_THE_BASE_CONTAINER.get(),
                    HeartOfTheBaseScreen::new);
            ScreenManager.register(ModContainers.ALCHEMICAL_FILTER_CONTAINER.get(),
                    AlchemicalFilterScreen::new);
            ScreenManager.register(ModContainers.UPGRADE_CONTAINER_CONTAINER.get(),
                    UpgradeContainerScreen::new);
            RenderTypeLookup.setRenderLayer(ModBlocks.BANNER_STAND.get(), RenderType.cutout());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("koronaswiat", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
