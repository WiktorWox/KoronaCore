package com.korona.koronaswiat.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItemTier;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;
import java.util.*;

public class AndurilWeapon extends SwordItem {
    public AndurilWeapon(Properties properties) {
        super(ModItemTier.SOUL, 3, -2.4F, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Here we can add effects on hit with this weapon to the target
        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        Random randomNumber = new Random(); //instance of random class
        if (attacker.swingTime == 0) {
            int int_random = randomNumber.nextInt(8);
            if (int_random == 0) {
                // Logger
                source.getCommands().performCommand(source.createCommandSourceStack(), "title " + target.getName().getString() + " title \"" + new TranslationTextComponent("text.koronaswiat.stun_text").getString() + "\"");
                // Send a message to the attacker that he hit a critical hit
                attacker.sendMessage(new TranslationTextComponent("text.koronaswiat.critical_hit"), attacker.getUUID());
                target.addEffect(new EffectInstance(Effects.BLINDNESS, 30, 1, true, true));
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 30, 5, true, true));
                target.addEffect(new EffectInstance(Effects.WEAKNESS, 30, 3, true, true));

            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.anduril"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.shift"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
