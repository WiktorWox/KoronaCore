package com.korona.koronaswiat.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class Villager {
    public static final RegistryObject<Block> ENDER_CHEST = RegistryObject.of(new ResourceLocation("minecraft:ender_chest"), ForgeRegistries.BLOCKS);
//    public static final RegistryObject<Item> BUNDLE = RegistryObject.of(new ResourceLocation("bundlesplus:bundle"), ForgeRegistries.ITEMS);

    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, KoronaSwiat.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(ForgeRegistries.PROFESSIONS, KoronaSwiat.MOD_ID);

    public static final RegistryObject<PointOfInterestType> BANKER_POI = POINT_OF_INTEREST_TYPE.register("banker_poi",
            () ->new PointOfInterestType("banker_poi", PointOfInterestType.getBlockStates(ENDER_CHEST.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> BANKER_PROFESSION = VILLAGER_PROFESSION.register("banker_prof",
            () ->new VillagerProfession("banker_prof", BANKER_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN));
    public static void registerPOI(){
        try {
            ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "registerBlockStates", PointOfInterestType.class).invoke(null, BANKER_POI.get());
        } catch (InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
        }
    }
    public static void fillTradeData() {
        MultiItemForEmeraldsTrade goldTrade = new MultiItemForEmeraldsTrade(ImmutableList.of(ModItems.CORONACOIN.get(), Items.GOLD_INGOT), ImmutableList.of(Items.GOLD_INGOT, ModItems.CORONACOIN.get()), ImmutableList.of(15, 8), ImmutableList.of(8, 12), 7, 6);
        MultiItemForEmeraldsTrade netheriteScrapTrade = new MultiItemForEmeraldsTrade(ImmutableList.of(ModItems.CORONACOIN.get(), Items.NETHERITE_SCRAP), ImmutableList.of(Items.NETHERITE_SCRAP, ModItems.CORONACOIN.get()), ImmutableList.of(31, 1), ImmutableList.of(1, 23), 3, 26);
        VillagerTrades.ITrade[] level1 = new VillagerTrades.ITrade[]{
                goldTrade,
                new BasicTrade(new ItemStack(Items.EMERALD, 0), new ItemStack(Items.EMERALD, 8), new ItemStack(ModItems.CORONACOIN.get(), 20), 35,  0, 0)
        };
        VillagerTrades.ITrade[] level2 = new VillagerTrades.ITrade[]{
                new BasicTrade(new ItemStack(ModItems.CORONACOIN.get(), 0), new ItemStack(ModItems.CORONACOIN.get(), 20), new ItemStack(Items.EMERALD, 8), 35,  0, 0),
                new BasicTrade(new ItemStack(Items.PRISMARINE_SHARD, 4), new ItemStack(ModItems.CORONACOIN.get(), 30), new ItemStack(Items.IRON_INGOT, 1), 3,  16, 0.3F)
        };
        VillagerTrades.ITrade[] level3 = new VillagerTrades.ITrade[]{
                new BasicTrade(new ItemStack(ModItems.CORONACOIN.get(), 64), new ItemStack(ModItems.MEGACORONACOIN.get(), 1), 3,  10, 0.5F),
                new BasicTrade(new ItemStack(Items.DIAMOND, 4), new ItemStack(ModItems.CORONACOIN.get(), 11), 7,  8, 0)
        };
        VillagerTrades.ITrade[] level4 = new VillagerTrades.ITrade[]{
                new BasicTrade(new ItemStack(Items.ENCHANTED_BOOK, 1), new ItemStack(ModItems.CORONACOIN.get(), 9), 5,  20, 0),
                new BasicTrade(new ItemStack(Items.DRAGON_HEAD, 1), new ItemStack(ModItems.CORONACOIN.get(), 20), 5,  24, 0)
        };
        VillagerTrades.ITrade[] level5 = new VillagerTrades.ITrade[]{
                netheriteScrapTrade,
//                new BasicTrade(new ItemStack(ModItems.MEGACORONACOIN.get(), 21), new ItemStack(ModItems.CORONACOIN.get(), 37), new ItemStack(BUNDLE.get(), 1), 3,  10, 0.5F)

        };
        VillagerTrades.TRADES.put(BANKER_PROFESSION.get(), toIntMap(ImmutableMap.of(1, level1, 2, level2, 3, level3, 4, level4, 5, level5)));
    }
    private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    public static class MultiItemForEmeraldsTrade implements VillagerTrades.ITrade {
        private final List<Item> items;
        private final List<Item> priceItems;
        private final List<Integer> amountOfItems;
        private final List<Integer> amountOfPriceItems;
        public final int uses;
        public final int villagerExp;
        public MultiItemForEmeraldsTrade(List<Item> priceItems, List<Item> items, List<Integer> amountOfPriceItems, List<Integer> amountOfItems, int uses, int villagerExp) {
            this.items = items;
            this.priceItems = priceItems;
            this.amountOfItems = amountOfItems;
            this.amountOfPriceItems = amountOfPriceItems;
            this.uses = uses;
            this.villagerExp = villagerExp;
        }
        @Nullable
        @Override
        public MerchantOffer getOffer(@Nonnull Entity entity, Random random) {
            int choose = (int) (random.nextFloat() * items.size());
            return new MerchantOffer(new ItemStack(priceItems.get(choose), amountOfPriceItems.get(choose)), new ItemStack(items.get(choose), amountOfItems.get(choose)), this.uses, this.villagerExp, 0.05F);
        }
    }
}
