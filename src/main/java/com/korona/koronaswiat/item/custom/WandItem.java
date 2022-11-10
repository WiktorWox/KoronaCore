package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.capabilities.WandCapabilityProvider;
import com.korona.koronaswiat.container.WandContainer;
import com.korona.koronaswiat.screen.WandScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class WandItem extends Item implements ICapabilityProvider {
    LazyOptional<ItemStackHandler> inventoryHandler = LazyOptional.of(ItemStackHandler::new);
    public WandItem() {
        super(new Properties()
                .tab(ItemGroup.TAB_COMBAT)
                .stacksTo(1));

    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Minecraft.getInstance().setScreen(new WandScreen( new WandContainer(player.getId(), world, player.blockPosition(), player.inventory, player), player.inventory, new TranslationTextComponent("screen.koronaswiat.wand")));
        return ActionResult.success(itemstack);

    };
    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity player) {
        ItemStack itemstack = super.finishUsingItem(itemStack, world, player);
        return player instanceof PlayerEntity && ((PlayerEntity)player).abilities.instabuild ? itemstack : new ItemStack(Items.GLASS_BOTTLE);
    }
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.DRINK;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHandler.cast();
        }
        return null;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new WandCapabilityProvider();
    }
}