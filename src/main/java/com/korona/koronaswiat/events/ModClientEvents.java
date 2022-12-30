
package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import com.korona.koronaswiat.guis.WandGui;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KoronaSwiat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onShiftWithBanner(TickEvent.PlayerTickEvent event) {
            if (Screen.hasShiftDown()) {
                PlayerEntity player = event.player;
                if (player.getMainHandItem().toString().contains("regeneration_banner") || player.getOffhandItem().toString().contains("regeneration_banner")) {
                    Vector3d playerPosition = player.position();
                    World world = player.getCommandSenderWorld();
                    addParticleSequence(playerPosition, world, -3.5D);
                    addParticleSequence(playerPosition, world, -3D);
                    addParticleSequence(playerPosition, world, -2.5D);
                    addParticleSequence(playerPosition, world, -2D);
                    addParticleSequence(playerPosition, world, -1.5D);
                    addParticleSequence(playerPosition, world, -1D);
                    addParticleSequence(playerPosition, world, -0.5D);
                    addParticleSequence(playerPosition, world, 0D);
                    addParticleSequence(playerPosition, world, 0.5D);
                    addParticleSequence(playerPosition, world, 1D);
                    addParticleSequence(playerPosition, world, 1.5D);
                    addParticleSequence(playerPosition, world, 2D);
                    addParticleSequence(playerPosition, world, 2.5D);
                    addParticleSequence(playerPosition, world, 3D);
                }
            }
    }

//    @SubscribeEvent
//    public static void onPlayerPlaceBlock(BlockEvent.EntityPlaceEvent event) {
//        if (!event.getWorld().isClientSide()) {
//            if (event.getEntity() instanceof PlayerEntity) {
//                PlayerEntity player = (PlayerEntity) event.getEntity();
//                ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
//                if (itemStack.getItem() == ModBlocks.HEART_OF_THE_BASE.get().asItem()) {
//                    event.setCanceled(true);
//                    String heartName = itemStack.getDisplayName().getString();
//                    if (RegionManager.get().containsRegion(heartName)) {
//                        player.sendMessage(new TranslationTextComponent("message.heart.wrong_name", heartName), player.getUUID());
//                        event.setCanceled(true);
//                    }
//                }
//            }
//        }
//    }

    private static void addParticleSequence(Vector3d playerPosition, World world, double distance) {
        world.addParticle(ParticleTypes.COMPOSTER, playerPosition.x + 3.5D,
                playerPosition.y + 0.5D, playerPosition.z + distance,
                0d,0.0d,0d);
        world.addParticle(ParticleTypes.COMPOSTER, playerPosition.x - 3.5D,
                playerPosition.y + 0.5D, playerPosition.z - distance,
                0d,0.0d,0d);
        world.addParticle(ParticleTypes.COMPOSTER, playerPosition.x - distance,
                playerPosition.y + 0.5D, playerPosition.z + 3.5D,
                0d,0.0d,0d);
        world.addParticle(ParticleTypes.COMPOSTER, playerPosition.x + distance,
                playerPosition.y + 0.5D, playerPosition.z - 3.5D,
                0d,0.0d,0d);
    }
}