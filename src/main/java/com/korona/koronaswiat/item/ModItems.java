package com.korona.koronaswiat.item;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.custom.*;
import com.korona.koronaswiat.item.custom.stone.MinasNeumaStone;
import com.korona.koronaswiat.item.custom.stone.Kiuskivi;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, KoronaSwiat.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // Added items:
    public static final RegistryObject<Item> CORONACOIN = ITEMS.register("coronacoin",
            () -> new Item(new Item.Properties()
                    .tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> MEGACORONACOIN = ITEMS.register(
            "megacoronacoin",
            () -> new Item(new Item.Properties()
                    .tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> FIRE_BALL = ITEMS.register("fire_ball",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)));

    public static final RegistryObject<Item> STRANGE_BREAD = ITEMS.register("strange_bread",
            () -> new Item(new Item.Properties()
                    .tab(ItemGroup.TAB_FOOD)
                    .food(new Food.Builder()
                            .effect(() -> new EffectInstance(Effects.DIG_SLOWDOWN, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.CONFUSION, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.WATER_BREATHING, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.BLINDNESS, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.HUNGER, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.WEAKNESS, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.POISON, 600, 3), 1.0F)
                            .effect(() -> new EffectInstance(Effects.BAD_OMEN, 36000, 0), 0.15F)
                            .saturationMod(0.8F)
                            .nutrition(5)
                            .alwaysEat()
                            .build())));
    public static final RegistryObject<Item> TEA_EARL_GREY = ITEMS.register(
            "tea_earl_grey",
            () -> new TeaItem());
    public static final RegistryObject<Item> TEA_YUNNAN = ITEMS.register(
            "tea_yunnan",
            () -> new TeaItem());
    public static final RegistryObject<Item> TEA_YERBA = ITEMS.register(
            "tea_yerba",
            () -> new TeaItem());
    public static final RegistryObject<Item> TEA_GREEN = ITEMS.register(
            "tea_green",
            () -> new TeaItem());
    public static final RegistryObject<Item> SOUL = ITEMS.register(
            "soul",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> DOOR_STONE = ITEMS.register(
            "door_stone",
            () -> new Item(new Item.Properties().tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1)));
    public static final RegistryObject<Item> SOUL_SAND_DUST = ITEMS.register(
            "soul_sand_dust",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> SOUL_INGOT = ITEMS.register(
            "soul_ingot",
            () -> new Item(new Item.Properties()
                    .tab(ItemGroup.TAB_MISC)));

    // -- EQUIPMENT
    public static final RegistryObject<Item> WAND = ITEMS.register(
            "wand",
            () -> new WandItem());

    public static final RegistryObject<Item> SCYTHE = ITEMS.register(
            "scythe",
            () -> new ScytheWeapon(new Item.Properties()
                    .tab(ItemGroup.TAB_COMBAT).stacksTo(1)));

    public static final RegistryObject<Item> ANDURIL = ITEMS.register(
            "anduril",
            () -> new AndurilWeapon(new Item.Properties()
                    .tab(ItemGroup.TAB_COMBAT)
                    .stacksTo(1)));

    public static final RegistryObject<Item> SOUL_PICKAXE = ITEMS.register(
            "soul_pickaxe",
            () -> new SoulPickaxeTool(new Item.Properties()
                    .tab(ItemGroup.TAB_TOOLS)
                    .stacksTo(1)));

    // -- MAGIC STONES
    public static final RegistryObject<MinasNeumaStone> STONE_MINAS_NEUMA_CRYSTAL = ITEMS.register(
            "stone_minas_neuma_crystal",
            () -> new MinasNeumaStone(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1), 240));

    public static final RegistryObject<Kiuskivi> STONE_KIUSKIVI = ITEMS.register(
            "stone_kiuskivi",
            () -> new Kiuskivi(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1), 240));
    public static final RegistryObject<Item> STONE_NECROMANCY = ITEMS.register(
            "stone_necromancy",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1)));
    public static final RegistryObject<Item> STONE_NECROMANCY_UPGRADED = ITEMS.register(
            "stone_necromancy_upgraded",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1)));

    // -- RUNESTONES & HOTB UPGRADES
    public static final RegistryObject<Item> RUNESTONE_BLUE = ITEMS.register(
            "runestone_blue",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(16)));
    public static final RegistryObject<Item> RUNESTONE_PURPLE = ITEMS.register(
            "runestone_purple",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(16)));
    public static final RegistryObject<Item> RUNESTONE_RED = ITEMS.register(
            "runestone_red",
            () -> new Item(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(16)));

    // -- BANNERS
    public static final RegistryObject<Item> REGENERATION_BANNER = ITEMS.register(
            "regeneration_banner",
            () -> new BannerItem(new Item.Properties()
                    .tab(ModItemGroup.MAGIC_ACCESSORIES)
                    .stacksTo(1), "minecraft:regeneration", 10, 1));
}