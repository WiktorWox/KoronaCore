package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.container.ModContainers;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.tileentity.WandTile;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class WandItem extends Item {
    public WandItem() {
        super(new Properties()
                .tab(ItemGroup.TAB_COMBAT)
                .stacksTo(1));

    }
    public ItemStack finishUsingItem(ItemStack p_77654_1_, World p_77654_2_, LivingEntity p_77654_3_) {
        ItemStack itemstack = super.finishUsingItem(p_77654_1_, p_77654_2_, p_77654_3_);
        return p_77654_3_ instanceof PlayerEntity && ((PlayerEntity)p_77654_3_).abilities.instabuild ? itemstack : new ItemStack(Items.GLASS_BOTTLE);
    }
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    }
//    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//        return null;
//    }
}