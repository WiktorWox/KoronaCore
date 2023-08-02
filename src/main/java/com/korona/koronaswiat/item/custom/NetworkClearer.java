package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.heartofthebase.INetwork;
import com.korona.koronaswiat.heartofthebase.NetworkManager;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.Collection;

public class NetworkClearer extends Item {
    public NetworkClearer() {
        super(new Properties()
                .tab(ItemGroup.TAB_FOOD)
                .food(new Food.Builder()
                        .effect(() -> new EffectInstance(Effects.REGENERATION, 1200, 0), 1.0F)
                        .saturationMod(0.1F).nutrition(5)
                        .alwaysEat()
                        .build())
                .stacksTo(1));

    }
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        Collection<INetwork> networksBefore = NetworkManager.get().getAllNetworks();
        NetworkManager.get().clearNetworks();
        Collection<INetwork> networksAfter = NetworkManager.get().getAllNetworks();
        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        source.getCommands().performCommand(source.createCommandSourceStack(), "/say Networks have been cleared!");
        source.getCommands().performCommand(source.createCommandSourceStack(), "/say Networks before: " + networksBefore);
        source.getCommands().performCommand(source.createCommandSourceStack(), "/say Networks after: " + networksAfter);
        if (networksBefore.size() == 0) {
            return ActionResult.fail(player.getItemInHand(hand));
        } else {
            return ActionResult.success(player.getItemInHand(hand));
        }
    }
}