
package com.korona.koronaswiat.events;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.screen.DiscScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = KoronaSwiat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onShiftWithBanner(TickEvent.PlayerTickEvent event) {
            if (Screen.hasShiftDown()) {
                PlayerEntity player = event.player;
                if (player.getMainHandItem().toString().contains("regeneration_banner") || player.getOffhandItem().toString().contains("regeneration_banner")) {
                    Vector3d playerPosition = player.position();
                    World world = player.getCommandSenderWorld();
                    addParticleSequence(playerPosition, world, -3.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -3D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -2.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -2D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -1.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -1D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, -0.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 0D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 0.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 1D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 1.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 2D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 2.5D, 3.5D, ParticleTypes.COMPOSTER);
                    addParticleSequence(playerPosition, world, 3D, 3.5D, ParticleTypes.COMPOSTER);
                }
            }
    }

    @SubscribeEvent
    public static void playerHurt (LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ModItems.SIARAS_BELLY.get())) {
                if (event.getSource().getEntity() != null) {
                    float f = -MathHelper.sin(player.yRot * ((float)Math.PI / 180F)) * MathHelper.cos(player.xRot * ((float)Math.PI / 180F));
                    float f1 = -MathHelper.sin((player.xRot + player.rotA) * ((float)Math.PI / 180F));
                    float f2 = MathHelper.cos(player.yRot * ((float)Math.PI / 180F)) * MathHelper.cos(player.xRot * ((float)Math.PI / 180F));
                    event.getSource().getEntity().push(f * 1.5, f1 * 1.5, f2 * 1.5);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyboardInput (InputEvent.KeyInputEvent event) {
        if (event.getKey() == 61) {
            Minecraft.getInstance().setScreen(new DiscScreen(new StringTextComponent("Disc")));
        }
//        else if (event.getKey() == 45) {
//            Minecraft.getInstance().player.getPersistentData().putString("discsFound", "null_null_null_null_null_null_null_null_null_null_null_null_null");
//            KoronaSwiat.LOGGER.info("Discs found reset");
//        }
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

    public static void addParticleSequence(Vector3d playerPosition, World world, double position, double distance, BasicParticleType particleType) {
        world.addParticle(particleType, playerPosition.x + distance,
                playerPosition.y + 0.5D, playerPosition.z + position,
                0d,0.0d,0d);
        world.addParticle(particleType, playerPosition.x - distance,
                playerPosition.y + 0.5D, playerPosition.z - position,
                0d,0.0d,0d);
        world.addParticle(particleType, playerPosition.x - position,
                playerPosition.y + 0.5D, playerPosition.z + distance,
                0d,0.0d,0d);
        world.addParticle(particleType, playerPosition.x + position,
                playerPosition.y + 0.5D, playerPosition.z - distance,
                0d,0.0d,0d);
    }

}