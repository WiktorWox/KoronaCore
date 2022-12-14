package com.korona.koronaswiat.util;

import com.korona.koronaswiat.KoronaSwiat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvent {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KoronaSwiat.MOD_ID);

    public static final RegistryObject<SoundEvent> ITEM_SCYTHE_HIT =
            registerSoundEvent("item_scythe_hit");
    public static final RegistryObject<SoundEvent> ITEM_SOUL_PICKAXE_CHARGE =
            registerSoundEvent("item_soul_pickaxe_charge");
    public static final RegistryObject<SoundEvent> ITEM_SOUL_PICKAXE_DISCHARGE =
            registerSoundEvent("item_soul_pickaxe_discharge");
    public static final RegistryObject<SoundEvent> ITEM_BANNER =
            registerSoundEvent("item_banner");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(KoronaSwiat.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
