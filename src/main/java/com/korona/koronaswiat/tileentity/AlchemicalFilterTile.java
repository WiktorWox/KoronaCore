package com.korona.koronaswiat.tileentity;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
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

public class AlchemicalFilterTile extends TileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private boolean itemsInSlots;
    private int craftingTickTime;


    public AlchemicalFilterTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public AlchemicalFilterTile() {
        this(ModTileEntities.ALCHEMICAL_FILTER_TILE.get());
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
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                if (itemHandler.getStackInSlot(0).getItem() == Items.WATER_BUCKET) {
                    itemHandler.getStackInSlot(0).shrink(1);
                    itemHandler.insertItem(0, new ItemStack(Items.BUCKET), false);
                    CompoundNBT nbt = AlchemicalFilterTile.this.getTileData();
                    nbt.putBoolean("usedWater", true);
                    AlchemicalFilterTile.this.save(nbt);
                }
                if (AlchemicalFilterTile.this.getTileData().getBoolean("usedWater") && itemHandler.getStackInSlot(1).getItem() == ModItems.SOUL_SAND_DUST.get() && itemHandler.getStackInSlot(2).getItem() == Items.GLASS_BOTTLE) {
                    itemsInSlots = true;
                } else {
                    itemsInSlots = false;
                    craftingTickTime = 0;
                }
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET;
                    case 1: return true;
                    case 2: return stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == ModItems.SOUL.get();
                    default: return false;
                }
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

    public void ticking() {
//        KoronaSwiat.LOGGER.info("Ticking!");
        if (itemsInSlots) {
            CompoundNBT nbt = AlchemicalFilterTile.this.getTileData();
            for (int counter = 0; counter<=10; counter++) {
                if (craftingTickTime/20 == counter) {
                    nbt.putInt("craftingProcess", counter);
                    AlchemicalFilterTile.this.save(nbt);
                }
            }
            if (craftingTickTime == 200) {
                itemHandler.getStackInSlot(1).shrink(1);
                itemHandler.getStackInSlot(2).shrink(1);
                itemHandler.insertItem(2, new ItemStack(ModItems.SOUL.get()), false);
                nbt.put("inv", itemHandler.serializeNBT());
                nbt.putInt("craftingProcess", 0);
                nbt.putBoolean("usedWater", false);
                AlchemicalFilterTile.this.save(nbt);
            }
            craftingTickTime ++;
        }
    }
}
