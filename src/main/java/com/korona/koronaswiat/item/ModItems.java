package com.korona.koronaswiat.item;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.custom.ScytheWeapon;
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

    // Dodane przedmioty:
    public static final RegistryObject<Item> CORONACOIN = ITEMS.register("coronacoin",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> STRANGE_BREAD = ITEMS.register("strange_bread",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(new Food.Builder().effect(() -> new EffectInstance(Effects.DIG_SLOWDOWN, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.CONFUSION, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.WATER_BREATHING, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.BLINDNESS, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.HUNGER, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.WEAKNESS, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 600, 3), 1.0F).effect(() -> new EffectInstance(Effects.BAD_OMEN, 36000, 0), 0.15F).saturationMod(0.8F).nutrition(5).alwaysEat().build())));
    public static final RegistryObject<Item> SOUL = ITEMS.register("soul",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> SOUL_SAND_DUST = ITEMS.register("soul_sand_dust",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> SOUL_INGOT = ITEMS.register("soul_ingot",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> SCYTHE = ITEMS.register("scythe",
            () -> new ScytheWeapon(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1)));
    public static final RegistryObject<Item> ANDURIL = ITEMS.register("anduril",
            () -> new ScytheWeapon(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1)));
    public static final RegistryObject<Item> MEGACORONACOIN = ITEMS.register("megacoronacoin",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
}