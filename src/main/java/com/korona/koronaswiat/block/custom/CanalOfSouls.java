package com.korona.koronaswiat.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.korona.koronaswiat.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class CanalOfSouls extends Block {

    public CanalOfSouls(Properties properties) {
        super(properties);
        for(BlockState blockstate : this.getStateDefinition().getPossibleStates()) {
            if (blockstate.getValue(POWER) == 0) {
                this.SHAPES_CACHE.put(blockstate, this.calculateShape(blockstate));
            }
        }
    }
    //BlockStates
    public static final BooleanProperty NORTH = BooleanProperty.create("soul_north");
    public static final BooleanProperty EAST = BooleanProperty.create("soul_east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("soul_south");
    public static final BooleanProperty WEST = BooleanProperty.create("soul_west");
    public static final BooleanProperty UP = BooleanProperty.create("soul_up");
    public static final BooleanProperty DOWN = BooleanProperty.create("soul_down");
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    private BlockState crossState;

    //VoxelShape controllers
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));

    private static final VoxelShape SHAPE_DOT = Block.box(6, 6, 6, 10, 10, 10);
    private static final Map<Direction, VoxelShape> SHAPES_SIDE = Maps.newEnumMap(ImmutableMap.of( Direction.NORTH, VoxelShapes.join(Block.box(6, 6, 1, 10, 10, 6), Block.box(5, 5, 0, 11, 11, 1), IBooleanFunction.OR), Direction.SOUTH, VoxelShapes.join(Block.box(6, 6, 10, 10, 10, 15), Block.box(5, 5, 15, 11, 11, 16), IBooleanFunction.OR), Direction.EAST, VoxelShapes.join(Block.box(10, 6, 6, 15, 10, 10), Block.box(15, 5, 5, 16, 11, 11), IBooleanFunction.OR), Direction.WEST, VoxelShapes.join(Block.box(1, 6, 6, 6, 10, 10), Block.box(0, 5, 5, 1, 11, 11), IBooleanFunction.OR)));
    private static final VoxelShape SHAPE_UP = VoxelShapes.join(Block.box(6, 10, 6, 10, 15, 10), Block.box(5, 15, 5, 11, 16, 11), IBooleanFunction.OR);
    private static final VoxelShape SHAPE_DOWN = VoxelShapes.join(Block.box(6, 1, 6, 10, 6, 10), Block.box(5, 0, 5, 11, 1, 11), IBooleanFunction.OR);

    private final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();
    private VoxelShape calculateShape(BlockState blockState) {
        VoxelShape voxelshape = SHAPE_DOT;
        this.crossState = this.defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false);

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            Boolean side = blockState.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (side == true) {
                voxelshape = VoxelShapes.or(voxelshape, SHAPES_SIDE.get(direction));
            }
        }
        if (blockState.getValue(UP) == true) {
            voxelshape = VoxelShapes.or(voxelshape, SHAPE_UP);
        }
        if (blockState.getValue(DOWN) == true) {
            voxelshape = VoxelShapes.or(voxelshape, SHAPE_DOWN);
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return this.SHAPES_CACHE.get(p_220053_1_.setValue( POWER, Integer.valueOf(0)));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getConnectionState(context.getLevel(), this.crossState, context.getClickedPos());
    }

    private BlockState getConnectionState(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        blockState = this.getMissingConnections(iBlockReader, blockState.setValue(POWER, blockState.getValue(POWER)), blockPos);
        return blockState;
    }

    private BlockState getMissingConnections(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPosDir = blockPos.relative(direction);
            BlockState blockStateDir = iBlockReader.getBlockState(blockPosDir);
            boolean canConnect = this.canConnectTo(blockStateDir, iBlockReader, blockPosDir, direction.getOpposite());
            blockState = blockState.setValue(PROPERTY_BY_DIRECTION.get(direction), canConnect);
        }
        for(Direction direction : Direction.Plane.VERTICAL) {
            if (direction == Direction.UP) {
                BlockPos blockPosDir = blockPos.above();
                BlockState blockStateDir = iBlockReader.getBlockState(blockPosDir);
                boolean canConnect = this.canConnectTo(blockStateDir, iBlockReader, blockPosDir, direction.getOpposite());
                blockState = blockState.setValue(UP, canConnect);
            }
            if (direction == Direction.DOWN) {
                BlockPos blockPosDir = blockPos.below();
                BlockState blockStateDir = iBlockReader.getBlockState(blockPosDir);
                boolean canConnect = this.canConnectTo(blockStateDir, iBlockReader, blockPosDir, direction.getOpposite());
                blockState = blockState.setValue(DOWN, canConnect);
            }
        }
        return blockState;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, POWER);
    }

    protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction direction) {
        if (blockState.is(ModBlocks.CANAL_OF_SOULS.get())) {
            return true;
        } else if (blockState.is(ModBlocks.HEART_OF_THE_BASE.get())) {
            return true;
        } else if (blockState.is(ModBlocks.UPGRADE_CONTAINER.get())) {
            return true;
        } else if (blockState.is(ModBlocks.BANNER_STAND.get())) {
            return true;
        } else {
            return false;
        }
    }

    public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        if (!blockState1.is(blockState.getBlock()) && !world.isClientSide) {

            for(Direction direction : Direction.Plane.VERTICAL) {
                world.updateNeighborsAt(blockPos.relative(direction), this);
            }
            this.updateNeighborsOfNeighboringCanals(world, blockPos);
        }
    }

    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        if (!world.isClientSide) {
            for(Direction direction : Direction.values()) {
                world.updateNeighborsAt(blockPos.relative(direction), this);
            }

            this.updateNeighborsOfNeighboringCanals(world, blockPos);
        }
    }

    private void updateNeighborsOfNeighboringCanals(World world, BlockPos blockPos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            this.checkCornerChangeAt(world, blockPos.relative(direction));
        }

        for(Direction direction1 : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = blockPos.relative(direction1);
            if (world.getBlockState(blockpos).isRedstoneConductor(world, blockpos)) {
                this.checkCornerChangeAt(world, blockpos.above());
            } else {
                this.checkCornerChangeAt(world, blockpos.below());
            }
        }
    }

    private void checkCornerChangeAt(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos).is(this)) {
            world.updateNeighborsAt(blockPos, this);

            for(Direction direction : Direction.values()) {
                world.updateNeighborsAt(blockPos.relative(direction), this);
            }

        }
    }

    private void update(BlockPos pos, World world) {
        Queue<BlockPos> queue = new LinkedList<>(); // create a queue to hold the positions of blocks that need to be updated
        Set<BlockPos> visited = new HashSet<>(); // create a set to hold the positions of visited blocks
        queue.add(pos); // add the starting block to the queue
        visited.add(pos); // add the starting block to the visited set

        while (!queue.isEmpty()) { // loop until all blocks in the queue have been processed
            BlockPos currentPos = queue.remove(); // get the next block position from the queue
            Block currentBlock = world.getBlockState(currentPos).getBlock(); // get the current block instance

            world.updateNeighborsAt(currentPos, currentBlock); // notify neighbors of the current block update

            // add all neighboring blocks to the queue for processing
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.offset(direction.getNormal());
                Block neighborBlock = world.getBlockState(neighborPos).getBlock();

                if (currentBlock.equals(neighborBlock) && !visited.contains(neighborPos)) {
                    queue.add(neighborPos);
                    visited.add(neighborPos);
                }
            }
        }
    }

    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        if (!world.isClientSide) {
            world.setBlockAndUpdate(blockPos, this.getConnectionState(world, blockState, blockPos));
        }
    }
}