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
    // Pobieramy dane ENDER_CHEST'a, aby użyć ich później podczas dodawania bankierowi "punktu uwagi"
    public static final RegistryObject<Block> ENDER_CHEST = RegistryObject.of(new ResourceLocation("minecraft:ender_chest"), ForgeRegistries.BLOCKS);

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
            e.printStackTrace();
        }
    }
    public static void fillTradeData() {
        MultiItemForEmeraldsTrade multiTrade = new MultiItemForEmeraldsTrade(ImmutableList.of(Items.ACACIA_BOAT, Items.OAK_BOAT), ImmutableList.of(1, 1, 1), ImmutableList.of(3, 4, 5), 5, 10);
        //                      profession level                                                            sell item        cost           in stock    vil exp gained
        VillagerTrades.ITrade[] level1 = new VillagerTrades.ITrade[]{new VillagerTrades.EmeraldForItemsTrade(ModItems.CORONACOIN.get(), 3, 30, 0), new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CORONACOIN.get(), 1, 3, 0)};
        VillagerTrades.ITrade[] level2 = new VillagerTrades.ITrade[]{new VillagerTrades.EmeraldForItemsTrade(Items.WHITE_WOOL, 12, 5, 10)};
        VillagerTrades.ITrade[] level3 = new VillagerTrades.ITrade[]{new VillagerTrades.EmeraldForItemsTrade(Items.OBSIDIAN, 12, 5, 10)};
        VillagerTrades.ITrade[] level4 = new VillagerTrades.ITrade[]{new VillagerTrades.EmeraldForItemsTrade(Items.OBSIDIAN, 12, 5, 10)};
        VillagerTrades.ITrade[] level5 = new VillagerTrades.ITrade[]{multiTrade};
        VillagerTrades.TRADES.put(BANKER_PROFESSION.get(), toIntMap(ImmutableMap.of(1, level1, 2, level2, 3, level3, 4, level4, 5, level5)));
    }
    private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    public static class MultiItemForEmeraldsTrade implements VillagerTrades.ITrade {
        private final List<Item> items;
        private final List<Integer> amountOfItems;
        private final List<Integer> amountOfEmeralds;
        public final int uses;
        public final int villagerExp;
        public MultiItemForEmeraldsTrade(List<Item> items, List<Integer> amountOfItems, List<Integer> amountOfEmeralds, int uses, int villagerExp) {
            this.items = items;
            this.amountOfItems = amountOfItems;
            this.amountOfEmeralds = amountOfEmeralds;
            this.uses = uses;
            this.villagerExp = villagerExp;
        }
        @Nullable
        @Override
        public MerchantOffer getOffer(@Nonnull Entity entity, Random random) {
            int choose = (int) (random.nextFloat() * items.size());
            return new MerchantOffer(new ItemStack(Items.EMERALD, amountOfEmeralds.get(choose)), new ItemStack(items.get(choose), amountOfItems.get(0)), this.uses, this.villagerExp, 0.05F);
        }
    }
}
