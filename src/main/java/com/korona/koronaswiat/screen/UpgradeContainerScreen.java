package com.korona.koronaswiat.screen;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.container.AlchemicalFilterContainer;
import com.korona.koronaswiat.container.UpgradeContainerContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class UpgradeContainerScreen extends ContainerScreen<UpgradeContainerContainer> {
    TileEntity tileEntity = menu.tileEntity.getLevel().getBlockEntity(menu.pos);
    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
            "textures/gui/upgrade_container_gui.png");

    public UpgradeContainerScreen(UpgradeContainerContainer upgradeContainerContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(upgradeContainerContainer, playerInventory, iTextComponent);
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

        //Czy slot 1 zawiera ulepszenie?
        if (tileEntity.getTileData().getBoolean("slot0IsUpgrade")) {

            //Wyświetl żółty kwadrat w koordynatach slotu 1
            this.blit(matrixStack, i + 44, j + 26, 176, 0, 16, 16);
        }

        if (tileEntity.getTileData().getBoolean("slot1IsUpgrade")) {
            this.blit(matrixStack, i + 62, j + 26, 176, 0, 16, 16);
        }
        if (tileEntity.getTileData().getBoolean("slot2IsUpgrade")) {
            this.blit(matrixStack, i + 80, j + 26, 176, 0, 16, 16);
        }
        if (tileEntity.getTileData().getBoolean("slot3IsUpgrade")) {
            this.blit(matrixStack, i + 98, j + 26, 176, 0, 16, 16);
        }
        if (tileEntity.getTileData().getBoolean("slot4IsUpgrade")) {
            this.blit(matrixStack, i + 116, j + 26, 176, 0, 16, 16);
        }
    }
}
