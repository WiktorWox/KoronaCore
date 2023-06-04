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
    public static final RegistryObject<SoundEvent> ITEM_STONE_MINASNEUMACRYSTAL =
            registerSoundEvent("item_stone_minasneumacrystal");
    public static final RegistryObject<SoundEvent> KYLE =
            registerSoundEvent("kyle");
    public static final RegistryObject<SoundEvent> LUIS_ABOUTPLACE =
            registerSoundEvent("luis_aboutplace");
    public static final RegistryObject<SoundEvent> LUIS_ATTACK =
            registerSoundEvent("luis_attack");
    public static final RegistryObject<SoundEvent> LUIS_IFTELLSOMEONE =
            registerSoundEvent("luis_iftellsomeone");
    public static final RegistryObject<SoundEvent> LUIS_INTRO =
            registerSoundEvent("luis_intro");
    public static final RegistryObject<SoundEvent> LUIS_INTRO2 =
            registerSoundEvent("luis_intro2");
    public static final RegistryObject<SoundEvent> LUIS_NEWS =
            registerSoundEvent("luis_news");
    public static final RegistryObject<SoundEvent> LUIS_STRUCTURE =
            registerSoundEvent("luis_structure");
    public static final RegistryObject<SoundEvent> LUIS_WHYHIDING =
            registerSoundEvent("luis_whyhiding");
    public static final RegistryObject<SoundEvent> DISC =
            registerSoundEvent("disc");
    public static final RegistryObject<SoundEvent> MESSAGE =
            registerSoundEvent("message");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT1 =
            registerSoundEvent("annoucement1");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT2 =
            registerSoundEvent("annoucement2");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT3 =
            registerSoundEvent("annoucement3");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT4 =
            registerSoundEvent("annoucement4");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT5 =
            registerSoundEvent("annoucement5");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT6 =
            registerSoundEvent("annoucement6");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT7 =
            registerSoundEvent("annoucement7");
    public static final RegistryObject<SoundEvent> ANNOUCEMENT8 =
            registerSoundEvent("annoucement8");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(KoronaSwiat.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
