package com.korona.koronaswiat.tileentity;

import com.korona.koronaswiat.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpgradeContainerTile extends TileEntity implements IInventory {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


    public UpgradeContainerTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public UpgradeContainerTile() {
        this(ModTileEntities.UPGRADE_CONTAINER_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {

            @Override
            //Gdy zawartość slotu się zmieni
            protected void onContentsChanged(int slot) {
                setChanged();
                //Jeśli zawartość tego slotu to dusza
                if (itemHandler.getStackInSlot(slot).getItem() == ModItems.SOUL.get()) {
                    //Zapisz do danych NBT, że w tym slocie jest ulepszenie
                    CompoundNBT nbt = UpgradeContainerTile.this.getTileData();
                    nbt.putBoolean("slot" + slot + "IsUpgrade", true);
                    UpgradeContainerTile.this.save(nbt);
                //W przeciwnym razie
                } else {
                    //Zapisz do danych NBT, że w tym slocie nie ma ulepszenia
                    CompoundNBT nbt = UpgradeContainerTile.this.getTileData();
                    nbt.putBoolean("slot" + slot + "IsUpgrade", false);
                    UpgradeContainerTile.this.save(nbt);
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return itemHandler.getStackInSlot(0) == ItemStack.EMPTY && itemHandler.getStackInSlot(1) == ItemStack.EMPTY && itemHandler.getStackInSlot(2) == ItemStack.EMPTY && itemHandler.getStackInSlot(3) == ItemStack.EMPTY && itemHandler.getStackInSlot(4) == ItemStack.EMPTY;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        itemHandler.getStackInSlot(slot).shrink(count);
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return null;
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        itemHandler.insertItem(slot, itemStack, false);
    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return true;
    }

    @Override
    public void clearContent() {

    }
}
