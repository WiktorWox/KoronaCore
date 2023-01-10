package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import com.korona.koronaswiat.container.UpgradeContainerContainer;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.tileentity.UpgradeContainerTile;
import fr.mosca421.worldprotector.core.Region;
import fr.mosca421.worldprotector.data.RegionManager;
import javafx.beans.property.StringProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Array;

public class CanalOfSouls extends Block {

    public CanalOfSouls(Properties properties) {
        super(properties);
    }

    public static final BooleanProperty UP = BooleanProperty.create("soul_up");
    public static final BooleanProperty DOWN = BooleanProperty.create("soul_down");
    public static final BooleanProperty NORTH = BooleanProperty.create("soul_north");
    public static final BooleanProperty EAST = BooleanProperty.create("soul_east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("soul_south");
    public static final BooleanProperty WEST = BooleanProperty.create("soul_west");
    public static final  BooleanProperty SOURCE_UP = BooleanProperty.create("source_up");
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, POWER);
    }

    public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
            int power = world.getBlockState(blockPos).getValue(POWER);
            KoronaSwiat.LOGGER.info("Power: " + power);
            // Log up, down, north, east, south, west
            KoronaSwiat.LOGGER.info("Up: " + world.getBlockState(blockPos).getValue(UP));
            KoronaSwiat.LOGGER.info("Down: " + world.getBlockState(blockPos).getValue(DOWN));
            KoronaSwiat.LOGGER.info("North: " + world.getBlockState(blockPos).getValue(NORTH));
            KoronaSwiat.LOGGER.info("East: " + world.getBlockState(blockPos).getValue(EAST));
            KoronaSwiat.LOGGER.info("South: " + world.getBlockState(blockPos).getValue(SOUTH));
            KoronaSwiat.LOGGER.info("West: " + world.getBlockState(blockPos).getValue(WEST));
        }
        return ActionResultType.SUCCESS;
    }

    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity player, ItemStack itemStack) {
        if (!world.isClientSide) {
            world.setBlockAndUpdate(blockPos, blockState.setValue(DOWN, false).setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(POWER, 0));
            // Checking for neighbour blocks
            Block blockAbove = world.getBlockState(blockPos.above()).getBlock();
            Block blockBelow = world.getBlockState(blockPos.below()).getBlock();
            Block blockNorth = world.getBlockState(blockPos.north()).getBlock();
            Block blockEast = world.getBlockState(blockPos.east()).getBlock();
            Block blockSouth = world.getBlockState(blockPos.south()).getBlock();
            Block blockWest = world.getBlockState(blockPos.west()).getBlock();
            checkForBlocks(world, blockPos, world.getBlockState(blockPos), blockAbove, blockBelow, blockNorth, blockEast, blockSouth, blockWest);
        }
    }
    public void checkForBlocks(World world, BlockPos blockPos, BlockState blockState, Block blockAbove, Block blockBelow, Block blockNorth, Block blockEast, Block blockSouth, Block blockWest) {
        BlockState state = blockState;
        boolean doUpdatePower = false;
        if (blockAbove == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.above()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(UP, true);
            updateCanalLook(world, blockPos.above(), DOWN, true);
        } else if (blockAbove == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(UP, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        if (blockBelow == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.below()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(DOWN, true);
            updateCanalLook(world, blockPos.below(), UP, true);
        } else if (blockBelow == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(DOWN, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        if (blockNorth == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.north()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(NORTH, true);
            updateCanalLook(world, blockPos.north(), SOUTH, true);
        } else if (blockNorth == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(NORTH, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        if (blockEast == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.east()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(EAST, true);
            updateCanalLook(world, blockPos.east(), WEST, true);
        } else if (blockEast == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(EAST, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        if (blockSouth == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.south()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(SOUTH, true);
            updateCanalLook(world, blockPos.south(), NORTH, true);
        } else if (blockSouth == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(SOUTH, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        if (blockWest == ModBlocks.CANAL_OF_SOULS.get()) {
            if (world.getBlockState(blockPos.west()).getValue(POWER) == 1) {
                state = state.setValue(POWER, 1);
                doUpdatePower = true;
            }
            state = state.setValue(WEST, true);
            updateCanalLook(world, blockPos.west(), EAST, true);
        } else if (blockWest == ModBlocks.HEART_OF_THE_BASE.get()) {
            state = state.setValue(WEST, true);
            state = state.setValue(POWER, 1);
            doUpdatePower = true;
        }
        world.setBlockAndUpdate(blockPos, state);
        if (doUpdatePower) {
            updatePower(world, blockPos, state);
        }
    }

    public static void updateCanalLook(World world, BlockPos blockPos, BooleanProperty updateDirection, boolean update) {
        BlockState state = world.getBlockState(blockPos);
        state = state.setValue(updateDirection, update);
        world.setBlockAndUpdate(blockPos, state);
    }

    public static void updatePower(World world, BlockPos blockPos, BlockState blockState) {
        BlockState state = blockState;
        if (state.getValue(UP)) {
            BlockState stateAbove = world.getBlockState(blockPos.above());
            if (stateAbove.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateAbove = stateAbove.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.above(), stateAbove);
                updatePower(world, blockPos.above(), stateAbove, DOWN);
            }
        }
        if (state.getValue(DOWN)) {
            BlockState stateBelow = world.getBlockState(blockPos.below());
            if (stateBelow.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateBelow = stateBelow.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.below(), stateBelow);
                updatePower(world, blockPos.below(), stateBelow, UP);
            }
        }
        if (state.getValue(NORTH)) {
            BlockState stateNorth = world.getBlockState(blockPos.north());
            if (stateNorth.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateNorth = stateNorth.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.north(), stateNorth);
                updatePower(world, blockPos.north(), stateNorth, SOUTH);
            }
        }
        if (state.getValue(EAST)) {
            BlockState stateEast = world.getBlockState(blockPos.east());
            if (stateEast.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateEast = stateEast.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.east(), stateEast);
                updatePower(world, blockPos.east(), stateEast, WEST);
            }
        }
        if (state.getValue(SOUTH)) {
            BlockState stateSouth = world.getBlockState(blockPos.south());
            if (stateSouth.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateSouth = stateSouth.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.south(), stateSouth);
                updatePower(world, blockPos.south(), stateSouth, NORTH);
            }
        }
        if (state.getValue(WEST)) {
            BlockState stateWest = world.getBlockState(blockPos.west());
            if (stateWest.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateWest = stateWest.setValue(POWER, 1);
                world.setBlockAndUpdate(blockPos.west(), stateWest);
                updatePower(world, blockPos.west(), stateWest, EAST);
            }
        }
    }
    public static void updatePower(World world, BlockPos blockPos, BlockState blockState, BooleanProperty noUpdateDirection) {
        BlockState state = blockState;
        if (!noUpdateDirection.equals(UP)) {
            if (state.getValue(UP)) {
                BlockState stateAbove = world.getBlockState(blockPos.above());
                if (stateAbove.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateAbove.getValue(POWER) == 0) {
                    stateAbove = stateAbove.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.above(), stateAbove);
                    updatePower(world, blockPos.above(), stateAbove);
                }
            }
        }
        if (!noUpdateDirection.equals(DOWN)) {
            if (state.getValue(DOWN)) {
                BlockState stateBelow = world.getBlockState(blockPos.below());
                if (stateBelow.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateBelow.getValue(POWER) == 0) {
                    stateBelow = stateBelow.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.below(), stateBelow);
                    updatePower(world, blockPos.below(), stateBelow);
                }
            }
        }
        if (!noUpdateDirection.equals(NORTH)) {
            if (state.getValue(NORTH)) {
                BlockState stateNorth = world.getBlockState(blockPos.north());
                if (stateNorth.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateNorth.getValue(POWER) == 0) {
                    stateNorth = stateNorth.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.north(), stateNorth);
                    updatePower(world, blockPos.north(), stateNorth);
                }
            }
        }
        if (!noUpdateDirection.equals(EAST)) {
            if (state.getValue(EAST)) {
                BlockState stateEast = world.getBlockState(blockPos.east());
                if (stateEast.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateEast.getValue(POWER) == 0) {
                    stateEast = stateEast.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.east(), stateEast);
                    updatePower(world, blockPos.east(), stateEast);
                }
            }
        }
        if (!noUpdateDirection.equals(SOUTH)) {
            if (state.getValue(SOUTH)) {
                BlockState stateSouth = world.getBlockState(blockPos.south());
                if (stateSouth.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateSouth.getValue(POWER) == 0) {
                    stateSouth = stateSouth.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.south(), stateSouth);
                    updatePower(world, blockPos.south(), stateSouth);
                }
            }
        }
        if (!noUpdateDirection.equals(WEST)) {
            if (state.getValue(WEST)) {
                BlockState stateWest = world.getBlockState(blockPos.west());
                if (stateWest.getBlock() == ModBlocks.CANAL_OF_SOULS.get() && stateWest.getValue(POWER) == 0) {
                    stateWest = stateWest.setValue(POWER, 1);
                    world.setBlockAndUpdate(blockPos.west(), stateWest);
                    updatePower(world, blockPos.west(), stateWest);
                }
            }
        }
    }





    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        if (blockState.getValue(DOWN)) {
            BlockState stateBelow = world.getBlockState(blockPos.below());
            if (stateBelow.getBlock() == ModBlocks.CANAL_OF_SOULS.get()) {
                stateBelow = stateBelow.setValue(POWER, 0);
                world.setBlockAndUpdate(blockPos.below(), stateBelow);
                updatePower(world, blockPos.below(), stateBelow);
            }
        }
    }
}