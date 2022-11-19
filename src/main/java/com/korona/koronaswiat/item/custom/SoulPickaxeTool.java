package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItemTier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SoulPickaxeTool extends PickaxeItem {
    public SoulPickaxeTool(Properties properties) {
        super(ModItemTier.SOUL, 5, -2.5F, properties);
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (Screen.hasAltDown()) {
            KoronaSwiat.LOGGER.info("WORKING!");
        }
        return ActionResult.success(itemstack);
    }

    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.soul_pickaxe"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.shift"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
