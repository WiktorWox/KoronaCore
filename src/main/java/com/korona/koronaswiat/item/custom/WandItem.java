package com.korona.koronaswiat.item.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.capabilities.WandCapabilityProvider;
import com.korona.koronaswiat.container.WandContainer;
import com.korona.koronaswiat.item.custom.stone.IMagicStoneItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
public class WandItem extends Item implements ICapabilityProvider {
    public LazyOptional<ItemStackHandler> inventoryHandler = LazyOptional.of(ItemStackHandler::new);

    public WandItem() {
        super(new Properties()
                .tab(ItemGroup.TAB_COMBAT)
                .stacksTo(1));

    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundNBT nbtData = getNbtData(itemstack);
        if (Screen.hasAltDown()) {
            int useItem;
            if (nbtData.contains("useItem")) {
                useItem = nbtData.getInt("useItem");
            } else {
                useItem = 0;
            }
            KoronaSwiat.LOGGER.info(useItem);
            if (useItem < 2) {
                nbtData.putInt("useItem", useItem + 1);
            } else {
                nbtData.putInt("useItem", 0);
            }
            itemstack.setTag(nbtData);
            if (!player.level.isClientSide() && Screen.hasControlDown()) {
                KoronaSwiat.LOGGER.info("ServerSide");
                NetworkHooks.openGui(((ServerPlayerEntity) player), new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.koronaswiat.wand");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new WandContainer(i, world, player.blockPosition(), playerInventory, playerEntity);
                    }
                }, player.blockPosition());
            }
        } else {
            if (!itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler()).getStackInSlot(nbtData.getInt("useItem")).isEmpty()) {
                // Getting stone as IMagicStoneItem
                IMagicStoneItem stone = (IMagicStoneItem) (itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler()).getStackInSlot(nbtData.getInt("useItem")).getItem());

                // Executing stone effect and setting cooldown after that
                stone.executeStoneEffect(world, player, hand);
                player.getCooldowns().addCooldown(this, stone.getCoolDownTime());
            }
        }
        return ActionResult.success(itemstack);
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