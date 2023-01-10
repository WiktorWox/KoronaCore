package com.korona.koronaswiat.tileentity;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.custom.HeartOfTheBase;
import com.korona.koronaswiat.item.ModItems;
import fr.mosca421.worldprotector.core.Region;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeartOfTheBaseTile extends TileEntity implements IInventory {

    public final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public final Integer LVL_0_RANGE = 4;
    public final Integer LVL_1_RANGE = 9;
    public final Integer LVL_2_RANGE = 15;
    public final Integer LVL_3_RANGE = 22;


    public HeartOfTheBaseTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public HeartOfTheBaseTile() {
        this(ModTileEntities.HEART_OF_THE_BASE_TILE.get());
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
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!Minecraft.getInstance().level.isClientSide()) {
                    KoronaSwiat.LOGGER.info(itemHandler.getStackInSlot(slot).getItem());
                    if (itemHandler.getStackInSlot(slot).getItem() == ModItems.RUNESTONE_BLUE.get()) {
                        changeRegionDistance(1);
                    } else if (itemHandler.getStackInSlot(slot).getItem() == ModItems.RUNESTONE_PURPLE.get()) {
                        changeRegionDistance(2);
                    } else if (itemHandler.getStackInSlot(slot).getItem() == ModItems.RUNESTONE_RED.get()) {
                        changeRegionDistance(3);
                    } else {
                        changeRegionDistance(0);
                    }
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (stack.getItem() == ModItems.RUNESTONE_BLUE.get() || stack.getItem() == ModItems.RUNESTONE_PURPLE.get() || stack.getItem() == ModItems.RUNESTONE_RED.get()) {
                    return true;
                } else return false;
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



    private void changeRegionDistance(Integer rangeLevel) {
        RegionManager regionManager = RegionManager.get();
        Minecraft minecraft = Minecraft.getInstance();
        this.getTileData().putInt("rangeLevel", rangeLevel);
        Integer lvlRange;
        switch(rangeLevel) {
            case 0:
                lvlRange = LVL_0_RANGE;
                break;
            case 1:
                lvlRange = LVL_1_RANGE;
                break;
            case 2:
                lvlRange = LVL_2_RANGE;
                break;
            case 3:
                lvlRange = LVL_3_RANGE;
                break;
            default:
                lvlRange = 0;
                break;
        }
        // Heart name log
        KoronaSwiat.LOGGER.info("Heart name: " + this.getTileData().getString("heart_name"));
        Region newRegion = (Region)regionManager.getRegion(this.getTileData().getString("heart_name")).get();
        newRegion.setArea(calculateArea(worldPosition, lvlRange));
        regionManager.updateRegion(newRegion, minecraft.player);
    }

    public AxisAlignedBB calculateArea(BlockPos blockPos, Integer levelRange) {
        BlockPos pos1 = new BlockPos(blockPos.getX() + levelRange, blockPos.getY() + levelRange, blockPos.getZ() + levelRange);
        BlockPos pos2 = new BlockPos(blockPos.getX() - levelRange, blockPos.getY() - levelRange, blockPos.getZ() - levelRange);
        return new AxisAlignedBB(pos1, pos2);
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
