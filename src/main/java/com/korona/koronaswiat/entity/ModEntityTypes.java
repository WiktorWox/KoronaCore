package com.korona.koronaswiat.entity;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.projectile.FireBallEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, KoronaSwiat.MOD_ID);

    // Entity Types
    public static final RegistryObject<EntityType<FireBallEntity>> FIRE_BALL = ENTITY_TYPES.register("fire_ball",
            () -> EntityType.Builder.of((EntityType.IFactory<FireBallEntity>) FireBallEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("fire_ball"));



}