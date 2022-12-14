package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;
import java.util.Random;

public class BannerStand extends Block {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
    public BannerStand(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        BlockState blockState = worldIn.getBlockEntity(pos).getBlockState();
        if (blockState.setValue(LEVEL, 1).equals(blockState)) {
            if (ModItems.REGENERATION_BANNER.get().equals(playerIn.getItemInHand(handIn).getItem())) {
                worldIn.setBlockAndUpdate(pos, worldIn.getBlockEntity(pos).getBlockState().setValue(LEVEL, 2));
                playerIn.inventory.removeItem(playerIn.getItemInHand(handIn));
            }
        }else if (blockState.setValue(LEVEL, 2).equals(blockState)) {
            worldIn.setBlockAndUpdate(pos, worldIn.getBlockEntity(pos).getBlockState().setValue(LEVEL, 1));
            MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            if (playerIn.isLocalPlayer())
                source.getCommands().performCommand(source.createCommandSourceStack(), "summon minecraft:item " + pos.getX() + " " + (pos.getY() + 0.5) + " " + pos.getZ() + " {Item:{id:\"koronaswiat:regeneration_banner\",Count:1b}}");
        }
        return ActionResultType.SUCCESS;
    }

    public void animateTick(BlockState blockStateIn, World worldIn, BlockPos blockPosIn, Random randomIn) {
        if (blockStateIn.setValue(LEVEL, 2).equals(blockStateIn)) {
            MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            String command = "execute positioned " + blockPosIn.getX() + " " + blockPosIn.getY() + " " + blockPosIn.getZ() + " run effect give @a[distance=0..3] minecraft:regeneration 1 1";
            source.getCommands().performCommand(source.createCommandSourceStack(), command);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(LEVEL, 1);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.BANNER_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
