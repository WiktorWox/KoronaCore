package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.item.ModItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class AndurilWeapon extends SwordItem {
    public AndurilWeapon(Properties properties) {
        super(ModItemTier.SOUL, 5, 0f, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Here we can add effects on hit with this weapon to the target
        attacker.addEffect(new EffectInstance(Effects.BLINDNESS, 60));
        target.addEffect(new EffectInstance(Effects.WITHER, 100, 1));
        return super.hurtEnemy(stack, target, attacker);
    }
}
