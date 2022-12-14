package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class BannerItem extends Item {
    String effectType;
    int effectTickTime;
    int effectLevel;

    public BannerItem(Item.Properties properties,  String effectType, int effectTickTime, int effectLevel) {
        super(properties);
        this.effectType = effectType;
        this.effectTickTime = effectTickTime;
        this.effectLevel = effectLevel;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.getCooldowns().addCooldown(this, 300);
        player.playSound(ModSoundEvent.ITEM_BANNER.get(), 18, 1);
        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        ItemStack itemstack = player.getItemInHand(hand);
        String command = "execute positioned " + player.position().x + " " + player.position().y + " " + player.position().z + " run effect give @a[distance=0..3] " + effectType + " " + effectTickTime + " " + effectLevel;
        source.getCommands().performCommand(source.createCommandSourceStack(), command);
        return ActionResult.success(itemstack);
    }
}
