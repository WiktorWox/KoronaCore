package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.container.UpgradeContainerContainer;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.tileentity.UpgradeContainerTile;
import fr.mosca421.worldprotector.core.IRegion;
import fr.mosca421.worldprotector.core.Region;
import fr.mosca421.worldprotector.data.RegionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
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

    //Gdy blok zostanie postawiony
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity player, ItemStack itemStack) {
        KoronaSwiat.LOGGER.info(RegionManager.get().getRegion("[nwm]"));
        String heartName = itemStack.getDisplayName().getString();
        if (!RegionManager.get().containsRegion(heartName)) {
            if (world.isClientSide) {
                TileEntity tileEntity = world.getBlockEntity(blockPos);
                tileEntity.getTileData().putString("player_owner", player.getName().getString());
                tileEntity.getTileData().putString("heart_name", heartName);
                //Wyznaczamy dwa wierzchołki naszego obrszaru
                BlockPos pos1 = new BlockPos(blockPos.getX() + 4, blockPos.getY() + 4, blockPos.getZ() + 4);
                BlockPos pos2 = new BlockPos(blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4);
                //Tworzymy Region(nazwa, obszar, wymiar)
                Region heartRegion = new Region(itemStack.getDisplayName().getString(), new AxisAlignedBB(pos1, pos2), world.dimension());
                //Dodajemy znacznik nie pozwalający na niszczenie i budowanie
                heartRegion.addFlag("break");
                heartRegion.addFlag("place");
                //Dodajemy gracza na którego nie działają znaczniki
                heartRegion.addPlayer((PlayerEntity) player);
                //Rejestrujemy region
                RegionManager.get().addRegion(heartRegion);
            }
        } else {
            if (!world.isClientSide) {
                player.sendMessage(new TranslationTextComponent("message.heart.wrong_name", heartName), player.getUUID());
                world.destroyBlock(blockPos, false);
            }
        }
    }

    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockState1, boolean b) {
        TileEntity tileentity = world.getBlockEntity(blockPos);
        RegionManager.get().removeRegion(tileentity.getTileData().getString("heart_name"));
        KoronaSwiat.LOGGER.info(tileentity.getTileData().getString("heart_name"));
        super.onRemove(blockState, world, blockPos, blockState1, b);
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