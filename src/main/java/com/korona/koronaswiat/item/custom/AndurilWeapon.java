package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.item.ModItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class AndurilWeapon extends SwordItem {
    public AndurilWeapon(Properties properties) {
        super(ModItemTier.SOUL, 5, 0f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Here we can add effects on hit with this weapon to the target
        target.addEffect(new EffectInstance(Effects.BLINDNESS, 30));
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 30, 5));
        target.addEffect(new EffectInstance(Effects.WEAKNESS, 30, 3));
        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        source.getCommands().performCommand(source.createCommandSourceStack(), "title " + target.getName() + " title \"Jesteś ogłuszony!\"");
        source.getCommands().performCommand(source.createCommandSourceStack(), "say hi");
        return super.hurtEnemy(stack, target, attacker);
    }
}
