package com.korona.koronaswiat.entity;

import com.google.common.collect.ImmutableSet;
import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;

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
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
