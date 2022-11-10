
package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.container.ModContainers;
import com.korona.koronaswiat.container.WandContainer;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.screen.WandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.GameData;

@Mod.EventBusSubscriber(modid = KoronaSwiat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

//    @SubscribeEvent (priority = EventPriority.HIGHEST)
//    public static void onCraftingTableOpen(PlayerInteractEvent.RightClickItem event) {
//        KoronaSwiat.LOGGER.info(event.getItemStack().getItem());
//        if(event.getItemStack().getItem() == ModItems.WAND.get().getItem()) {
//            KoronaSwiat.LOGGER.info("true");
//            Minecraft.getInstance().setScreen(new WandScreen( new WandContainer(event.getPlayer().getId(), event.getPlayer().level, event.getPos(), event.getPlayer().inventory, event.getPlayer()), event.getPlayer().inventory, new TranslationTextComponent("screen.koronaswiat.wand")));
//        }
//    }

    @SubscribeEvent // LivingEntity#func_233580_cy_c() ----> LivingEntity#getPosition()
    public static void onJumpWithStick(LivingEvent.LivingJumpEvent event) {
        LivingEntity player = event.getEntityLiving();
        if (player.getMainHandItem().getItem() == Items.STICK) {
            KoronaSwiat.LOGGER.info("Player tried to jump with a stick!");
        }
    }
}