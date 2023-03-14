package com.korona.koronaswiat.block.custom;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.tileentity.ModTileEntities;
import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

public class Disc extends Block {
    public Disc(Properties properties) {
        super(properties);
    }
    public static final BooleanProperty FOUND = BooleanProperty.create("found");
    public static final IntegerProperty DISC_TYPE = IntegerProperty.create("disc_type", 0, 12);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FOUND, DISC_TYPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FOUND, false).setValue(DISC_TYPE, 0);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        worldIn.addParticle(ParticleTypes.ENCHANT, pos.getX() + rand.nextDouble(),
                pos.getY() + 1D, pos.getZ() + rand.nextDouble(),
                0d,0.05d,0d);
        worldIn.addParticle(ParticleTypes.FIREWORK, pos.getX() + rand.nextDouble(),
                pos.getY() + 1D, pos.getZ() + rand.nextDouble(),
                0d,0.05d,0d);

//            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, stateIn), pos.getX() + rand.nextDouble(),
//                    pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(),
//                    0.0D, 0.05D, 0.0D);

        super.animateTick(stateIn, worldIn, pos, rand);
    }

    public ActionResultType use(BlockState blockState, World worldIn, BlockPos blockPos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BlockState state = worldIn.getBlockState(blockPos);
        if (player.getName().getString().equals("Dev") && Screen.hasShiftDown() || player.getName().getString().equals("Brivor") && Screen.hasShiftDown()) {
            if (blockState.getValue(DISC_TYPE) != 12) {
                state = state.setValue(DISC_TYPE, blockState.getValue(DISC_TYPE) + 1);
            } else {
                state = state.setValue(DISC_TYPE, 0);
            }
        } else {
            String[] discsData;
            if (player.getPersistentData().getString("discsFound").split("_").length != 13) {
                discsData = new String[]{"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"};
            } else {
                discsData = player.getPersistentData().getString("discsFound").split("_");
            }
            if (discsData[blockState.getValue(DISC_TYPE)].equals("null")) {
                if (blockState.getValue(FOUND) == false) {
                    discsData[blockState.getValue(DISC_TYPE)] = "gold";
                } else {
                    discsData[blockState.getValue(DISC_TYPE)] = "stone";
                }
                saveDiscsData(player, discsData);
                if (!worldIn.isClientSide()) {
                    state = state.setValue(FOUND, true);
                }
                worldIn.playSound(Minecraft.getInstance().player, blockPos, ModSoundEvent.DISC.get(), null, 1.0f, 1.0f);
                KoronaSwiat.LOGGER.info("Discs found: " + player.getPersistentData().getString("discsFound"));
                // Minecraft.getInstance().player.getPersistentData().putString("discsFound", String.join("_", Arrays.toString(new String[]{"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"}).replace("[", "").replace("]", "").replace(" ", "").split(",")))
            } else {
            }
        }
        worldIn.setBlockAndUpdate(blockPos, state);
        return ActionResultType.SUCCESS;
    }

    private void saveDiscsData(PlayerEntity player, String[] discsData) {
        player.getPersistentData().putString("discsFound    ", String.join("_", Arrays.toString(discsData).replace("[", "").replace("]", "").replace(" ", "").split(",")));
    }

    public void setPlacedBy(World worldIn, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity player, ItemStack itemStack) {
        worldIn.setBlockAndUpdate(blockPos, worldIn.getBlockState(blockPos).setValue(FOUND, false));
        saveDiscsData((PlayerEntity)player, new String[]{"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"});
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.DISC_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

//    @Override
//    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
//        return BlockRenderType.ENTITYBLOCK_ANIMATED;
//    }
}