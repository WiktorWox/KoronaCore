package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.item.ModItemTier;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SoulPickaxeTool extends PickaxeItem {
    Integer remainingBlocks;
    public SoulPickaxeTool(Properties properties) {
        super(ModItemTier.SOUL, 5, -2.3F, properties);
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);
        //there we are checking that item is used with ALT down
        if (Screen.hasAltDown()) {
            //If is soul in inventory, this will be run
            if (playerIn.inventory.contains(ModItems.SOUL.get().getDefaultInstance())) {
                //We are adding a new NBTdata or taking an existing one. Then we are adding a data to it
                if (itemStack.hasTag()) {
                    remainingBlocks = itemStack.getTag().getInt("RemainingBlocks");
                } else {
                    remainingBlocks = 0;
                }
                CompoundNBT nbtData;
                nbtData = getNbtData(itemStack);
                nbtData.putDouble("CustomModelData", 2);
                nbtData.putInt("RemainingBlocks", remainingBlocks + 25);
                itemStack.setTag(nbtData);

                playerIn.inventory.removeItem(playerIn.inventory.findSlotMatchingItem(ModItems.SOUL.get().getDefaultInstance()), 1);
                if (remainingBlocks == 0) {
                    playerIn.level.playSound((PlayerEntity) null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvent.ITEM_SOUL_PICKAXE_CHARGE.get(), SoundCategory.PLAYERS, 18, 1);
                }
            }
        }
        return ActionResult.success(itemStack);
    }

    private CompoundNBT getNbtData(ItemStack itemStack) {
        CompoundNBT nbtData;
        if (itemStack.hasTag()) {
            nbtData = itemStack.getTag();
        } else {
            nbtData = new CompoundNBT();
        }
        return nbtData;
    }

    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        Material material = blockState.getMaterial();
        //If soul pickaxe is charged we're adding a value to default destroy speed
        float nativeDestroySpeed = material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE ? super.getDestroySpeed(itemStack, blockState) : this.speed;

        if (itemStack.getTag().getInt("CustomModelData") == 2) {
            return nativeDestroySpeed + 17;
        } else {
            return nativeDestroySpeed;
        }
    }

    public boolean mineBlock(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        remainingBlocks = itemStack.getTag().getInt("RemainingBlocks");
        CompoundNBT nbtData;
        nbtData = getNbtData(itemStack);
        //Updating a NBTdata
        switch (remainingBlocks) {
            case 1:
                onBreakBlock(nbtData, 1, itemStack, livingEntity);
                livingEntity.level.playSound((PlayerEntity) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSoundEvent.ITEM_SOUL_PICKAXE_DISCHARGE.get(), SoundCategory.PLAYERS, 18, 1);
                break;
            case 0:
                break;
            default:
                onBreakBlock(nbtData, 2, itemStack, livingEntity);
                break;
        }
        if (!world.isClientSide && blockState.getDestroySpeed(world, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, (t) -> {
                t.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    private void onBreakBlock(CompoundNBT nbtData, int p_74780_2_, ItemStack itemStack, LivingEntity livingEntity) {
        nbtData.putDouble("CustomModelData", p_74780_2_);
        nbtData.putInt("RemainingBlocks", remainingBlocks - 1);
        itemStack.setTag(nbtData);
        livingEntity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 80, 0));
    }


    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getTag().getShort("CustomModelData") != 2) {
            if (Screen.hasShiftDown()) {
                tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.soul_pickaxe"));
            } else {
                tooltip.add(new TranslationTextComponent("tooltip.koronaswiat.shift"));
            }
        } else {
            String tooltipSoulPickaxeCharged = new TranslationTextComponent("tooltip.koronaswiat.soul_pickaxe.charged").getString();
            String tooltipSoulPickaxeTime = new TranslationTextComponent("tooltip.koronaswiat.soul_pickaxe.time").getString();
            remainingBlocks = stack.getTag().getInt("RemainingBlocks");
            StringTextComponent tooltipText = new StringTextComponent(tooltipSoulPickaxeCharged + remainingBlocks + tooltipSoulPickaxeTime);
            tooltip.add(tooltipText);
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
