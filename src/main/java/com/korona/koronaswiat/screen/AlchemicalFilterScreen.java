package com.korona.koronaswiat.screen;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.container.AlchemicalFilterContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlchemicalFilterScreen extends ContainerScreen<AlchemicalFilterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
            "textures/gui/alchemical_filter_gui.png");

    public AlchemicalFilterScreen(AlchemicalFilterContainer alchemicalFilterContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(alchemicalFilterContainer, playerInventory, iTextComponent);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float particalTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
//        KoronaSwiat.LOGGER.info(menu.tileEntity.getLevel().getBlockEntity(menu.pos).getTileData().getInt("craftingProcess"));
        if(menu.tileEntity.getLevel().getBlockEntity(menu.pos).getTileData().getBoolean("usedWater")) {
            this.blit(matrixStack, i + 50, j + 17, 176, 0, 40, 23);
        }
        Integer craftingProcess = menu.tileEntity.getLevel().getBlockEntity(menu.pos).getTileData().getInt("craftingProcess");
        KoronaSwiat.LOGGER.info((menu.tileEntity.getLevel().getBlockEntity(menu.pos).getTileData().getInt("craftingProcess") == 10));
        switch (craftingProcess) {
            case 1:
                this.blit(matrixStack, i + 81, j + 44, 176, 23, 14, 13);
                break;
            case 2:
                this.blit(matrixStack, i + 81, j + 44, 176, 36, 14, 13);
                break;
            case 3:
                this.blit(matrixStack, i + 81, j + 44, 176, 49, 14, 13);
                break;
            case 4:
                this.blit(matrixStack, i + 81, j + 44, 190, 23, 14, 13);
                break;
            case 5:
                this.blit(matrixStack, i + 81, j + 44, 190, 36, 14, 13);
                break;
            case 6:
                this.blit(matrixStack, i + 81, j + 44, 190, 62, 14, 13);
                break;
            case 7:
                this.blit(matrixStack, i + 81, j + 44, 204, 23, 14, 13);
                break;
            case 8:
                this.blit(matrixStack, i + 81, j + 44, 204, 49, 14, 13);
                break;
            case 9:
                this.blit(matrixStack, i + 81, j + 44, 204, 62, 14, 13);
                break;
            case 10:
                this.blit(matrixStack, i + 81, j + 44, 218, 23, 14, 13);
                break;
        }
    }
}
