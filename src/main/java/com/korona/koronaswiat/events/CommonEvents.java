package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.Villager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = KoronaSwiat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
            Villager.registerPOI();
            Villager.fillTradeData();
    }
}
