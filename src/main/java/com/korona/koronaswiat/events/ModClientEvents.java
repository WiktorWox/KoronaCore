
package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
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