package com.korona.koronaswiat.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Tea extends Item {
    public Tea() {
        super(new Item.Properties()
                .tab(ItemGroup.TAB_FOOD)
                .food(new Food.Builder()
                        .effect(() -> new EffectInstance(Effects.REGENERATION, 1200, 0), 1.0F)
                        .saturationMod(0.1F).nutrition(5)
                        .alwaysEat()
                        .build())
                .stacksTo(1));

    }
    public ItemStack finishUsingItem(ItemStack p_77654_1_, World p_77654_2_, LivingEntity p_77654_3_) {
        ItemStack itemstack = super.finishUsingItem(p_77654_1_, p_77654_2_, p_77654_3_);
        return p_77654_3_ instanceof PlayerEntity && ((PlayerEntity)p_77654_3_).abilities.instabuild ? itemstack : new ItemStack(Items.GLASS_BOTTLE);
    }
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    }
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return DrinkHelper.useDrink(p_77659_1_, p_77659_2_, p_77659_3_);
    }
}