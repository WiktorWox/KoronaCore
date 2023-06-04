package com.korona.koronaswiat.capabilities;

import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.item.custom.stone.IMagicStoneItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class WandCapabilityProvider implements ICapabilitySerializable<INBT> {

    public ItemStackHandler itemHandler = createHandler();

    // This instantiates the Inventory only when it is first requested, and then caches it
    @Nonnull
    public ItemStackHandler getCachedInventory() {
        if (itemHandler == null) {
            itemHandler = new ItemStackHandler();
        }
        return itemHandler;
    }

    private final LazyOptional<IItemHandler> lazyInventory = LazyOptional.of(this::getCachedInventory);
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof IMagicStoneItem;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    // Provides the Inventory
    @Nonnull @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        // If we needed to provide more than one capability, we'd simply add another check:
        // if (cap == SOME_OTHER_CAPABILITY) return (otherCapability)

        return LazyOptional.empty();
    }

    // Saves the Inventory data to an NBT tag, so that it can be saved to disk
    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    // Reads the Inventory data from an NBT tag that was saved to disk
    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), null, nbt);
    }
}