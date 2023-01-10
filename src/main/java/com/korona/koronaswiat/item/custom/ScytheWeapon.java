package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItemTier;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ScytheWeapon extends SwordItem {
    public ScytheWeapon(Properties properties) {
        super(ModItemTier.SCYTHE, 0, -2.0F, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Here we can add effects on hit with this weapon to the target
        KoronaSwiat.LOGGER.info(attacker.swingTime);
        if (attacker.swingTime == 0) {
            attacker.level.playSound((PlayerEntity) null, attacker.getX(), attacker.getY(), attacker.getZ(), ModSoundEvent.ITEM_SCYTHE_HIT.get(), SoundCategory.PLAYERS, 18, 1);
            attacker.addEffect(new EffectInstance(Effects.HEAL, 1, 0));
            target.addEffect(new EffectInstance(Effects.WITHER, 100, 1));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
