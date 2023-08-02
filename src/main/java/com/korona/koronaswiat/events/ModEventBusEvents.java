package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.ModEntityTypes;
import com.korona.koronaswiat.entity.custom.RaidfarmingEntity;
import com.korona.koronaswiat.item.custom.ModSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KoronaSwiat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.RAIDFARMING.get(), RaidfarmingEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initSpawnEggs();
    }
}
