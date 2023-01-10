package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.block.ModBlocks;
import com.korona.koronaswiat.container.HeartOfTheBaseContainer;
import com.korona.koronaswiat.container.UpgradeContainerContainer;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.tileentity.HeartOfTheBaseTile;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.tileentity.UpgradeContainerTile;
import fr.mosca421.worldprotector.core.IRegion;
import fr.mosca421.worldprotector.core.Region;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import java.util.Optional;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.region;

public class HeartOfTheBase extends HorizontalBlock {
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
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(!player.isCrouching()) {
                if(tileEntity instanceof HeartOfTheBaseTile) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
                    //We are openning a heart gui
                    NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getBlockPos());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            } else {
                if(tileEntity instanceof HeartOfTheBaseTile) {
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
            }
        } else {
            if (!world.isClientSide) {
                //Send warning message
                player.sendMessage(new TranslationTextComponent("message.heart.wrong_name", heartName), player.getUUID());
                //Destroy block
                world.destroyBlock(blockPos, false);
                //Drop block item
                InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModBlocks.HEART_OF_THE_BASE.get().asItem()));
            }
        }
    }

    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        TileEntity tileentity = world.getBlockEntity(blockPos);
        //We are removing a region with block item name from the world
        RegionManager.get().removeRegion(tileentity.getTileData().getString("heart_name"));
        super.onRemove(blockState, world, blockPos, blockState1, b);
        if (!blockState.is(blockState1.getBlock())) {
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropContents(world, blockPos, (IInventory)tileentity);
                world.updateNeighbourForOutputSignal(blockPos, this);
            }

            super.onRemove(blockState, world, blockPos, blockState1, b);
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