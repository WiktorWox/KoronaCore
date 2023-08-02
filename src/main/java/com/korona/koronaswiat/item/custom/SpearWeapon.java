package com.korona.koronaswiat.item.custom;

import java.util.function.Predicate;

import com.korona.koronaswiat.entity.ModEntityTypes;
import com.korona.koronaswiat.entity.custom.projectile.FireBallEntity;
import com.korona.koronaswiat.entity.custom.projectile.SpearEntity;
import com.korona.koronaswiat.entity.render.SpearRenderer;
import com.korona.koronaswiat.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SpearWeapon extends ShootableItem implements IVanishable {
    public SpearWeapon(Item.Properties p_i48522_1_) {
        super(p_i48522_1_);
    }

    public void releaseUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int i1) {
//        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)livingEntity;
            int i = this.getUseDuration(itemStack) - i1;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, world, playerentity, i, !itemstack.isEmpty() || flag);
//            if (i < 0) return;

            SpearEntity spearEntity = new SpearEntity(ModEntityTypes.SPEAR.get(), playerentity, playerentity.level);
            spearEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, getPowerForTime(i), 1.0F);
            playerentity.level.addFreshEntity(spearEntity);
//        }
    }

    public static float getPowerForTime(int p_185059_0_) {
        float f = (float)p_185059_0_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        p_77659_2_.startUsingItem(p_77659_3_);
        return ActionResult.consume(itemstack);
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return itemStack -> true;
    }

    public int getDefaultProjectileRange() {
        return 15;
    }
}
