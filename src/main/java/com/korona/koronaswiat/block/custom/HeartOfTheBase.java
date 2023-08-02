package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import com.korona.koronaswiat.container.HeartOfTheBaseContainer;
import com.korona.koronaswiat.heartofthebase.Network;
import com.korona.koronaswiat.heartofthebase.NetworkManager;
import com.korona.koronaswiat.tileentity.HeartOfTheBaseTile;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import fr.mosca421.worldprotector.core.Region;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class HeartOfTheBase extends HorizontalBlock implements INetworkBlock {
    public HeartOfTheBase(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos,
                                PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if (!player.isCrouching()) {
                if (tileEntity instanceof HeartOfTheBaseTile) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
                    //We are openning a heart gui
                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getBlockPos());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            } else {
                if (tileEntity instanceof HeartOfTheBaseTile) {
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.koronaswiat.heart_of_the_base", worldIn.getBlockEntity(pos).getTileData().getString("heart_name"));
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new HeartOfTheBaseContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction direction) {
        if (blockState.is(ModBlocks.CANAL_OF_SOULS.get())) {
            return true;
        } else if (blockState instanceof INetworkBlock) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity player, ItemStack itemStack) {
        KoronaSwiat.LOGGER.info(RegionManager.get().getRegion("[nwm]"));
        String heartName = itemStack.getDisplayName().getString();
        //If there is no Region with name from item
        if (!RegionManager.get().containsRegion(heartName)) {
            if (!world.isClientSide) {
                TileEntity tileEntity = world.getBlockEntity(blockPos);
                //We are saving a name of block owner and name of the block item
                tileEntity.getTileData().putString("player_owner", player.getName().getString());
                tileEntity.getTileData().putString("heart_name", heartName);
                //Determining vertex of our Region area
                BlockPos pos1 = new BlockPos(blockPos.getX() + 4, blockPos.getY() + 4, blockPos.getZ() + 4);
                BlockPos pos2 = new BlockPos(blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4);
                //We are makin' Region(name, area, dimension)
                Region heartRegion = new Region(itemStack.getDisplayName().getString(), new AxisAlignedBB(pos1, pos2), world.dimension());
                //We are adding flags that make a zone protected from destruction and placing blocks
                heartRegion.addFlag("break");
                heartRegion.addFlag("place");
                //We are adding flag-resistant players
                heartRegion.addPlayer((PlayerEntity) player);
                //We are registering a Region
                RegionManager.get().addRegion(heartRegion);
                Network network = new Network(heartName, world.dimension());
                network.addBlock(blockPos);
                NetworkManager.get().addNetwork(network);
                update(blockPos, world);
                KoronaSwiat.LOGGER.info(NetworkManager.get().getAllNetworks());
            }
        } else {
            if (!world.isClientSide) {
                //Send warning message
                player.sendMessage(new TranslationTextComponent("message.heart.wrong_name", heartName), player.getUUID());
                //Drop block item
                InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModBlocks.HEART_OF_THE_BASE.get().asItem()));
            }
            //Destroy block
            world.destroyBlock(blockPos, false);
        }
    }

    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        TileEntity tileentity = world.getBlockEntity(blockPos);
        //We are removing a region with block item name from the world
        RegionManager.get().removeRegion(tileentity.getTileData().getString("heart_name"));
        super.onRemove(blockState, world, blockPos, blockState1, b);
        if (!blockState.is(blockState1.getBlock())) {
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropContents(world, blockPos, (IInventory) tileentity);
                world.updateNeighbourForOutputSignal(blockPos, this);
            }

            super.onRemove(blockState, world, blockPos, blockState1, b);
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

                if (canConnectTo(world.getBlockState(neighborPos), world, neighborPos, null) && !visited.contains(neighborPos)) {
                    queue.add(neighborPos);
                    visited.add(neighborPos);
                }
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.HEART_OF_THE_BASE_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}