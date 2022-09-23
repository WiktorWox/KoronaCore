package com.korona.koronaswiat.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.korona.koronaswiat.item.ModItemTier;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.Random;

public class AndurilWeapon extends SwordItem {
    public AndurilWeapon(Properties properties) {
        super(ModItemTier.SOUL, 5, 0f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Here we can add effects on hit with this weapon to the target
        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        Random randomNumber = new Random(); //instance of random class
        int int_random = randomNumber.nextInt(5);
        if (int_random == 0) {
            source.getCommands().performCommand(source.createCommandSourceStack(), "title " + target.getName().getString() + " title \"Jesteś ogłuszony!\"");
            target.addEffect(new EffectInstance(Effects.BLINDNESS, 30, 1, true, true));
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 30, 5, true, true));
            target.addEffect(new EffectInstance(Effects.WEAKNESS, 30, 3, true, true));
            attacker.level.playSound((PlayerEntity)null, attacker.getX(), attacker.getY(), attacker.getZ(), ModSoundEvent.ITEM_SCYTHE_HIT.get(), SoundCategory.PLAYERS, 18, 1);
        }

        source.getCommands().performCommand(source.createCommandSourceStack(), "say Zaatakowano gracza "+ target.getName().getString());
        return super.hurtEnemy(stack, target, attacker);
    }
}
