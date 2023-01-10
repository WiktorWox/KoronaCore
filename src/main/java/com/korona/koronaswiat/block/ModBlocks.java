package com.korona.koronaswiat.block;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.custom.*;
import com.korona.koronaswiat.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, KoronaSwiat.MOD_ID);

    public static final RegistryObject<Block> ALCHEMICAL_FILTER = registerBlock("alchemical_filter",
            () -> new AlchemicalFilter(AbstractBlock.Properties.of(Material.DECORATION)
                    .harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5f).noOcclusion()));

    public static final RegistryObject<Block> BANNER_STAND = registerBlock("banner_stand",
            () -> new BannerStand(AbstractBlock.Properties.of(Material.DECORATION)
                    .harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5f).noOcclusion()));

    public static final RegistryObject<Block> UPGRADE_CONTAINER = registerBlock("upgrade_container",
            () -> new UpgadeContainer(AbstractBlock.Properties.of(Material.DECORATION)
                    .harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5f).noOcclusion()));

    public static final RegistryObject<Block> HEART_OF_THE_BASE = registerBlock("heart_of_the_base",
            () -> new HeartOfTheBase(AbstractBlock.Properties.of(Material.DECORATION)
                    .harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5f).noOcclusion()));

    public static final RegistryObject<Block> CANAL_OF_SOULS = registerBlock("canal_of_souls",
            () -> new CanalOfSouls(AbstractBlock.Properties.of(Material.DECORATION)
                    .harvestLevel(2).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5f).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
