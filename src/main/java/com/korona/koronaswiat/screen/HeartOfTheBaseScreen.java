package com.korona.koronaswiat.screen;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.container.HeartOfTheBaseContainer;
import com.korona.koronaswiat.container.UpgradeContainerContainer;
import com.korona.koronaswiat.item.ModItems;
import com.korona.koronaswiat.tileentity.HeartOfTheBaseTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HeartOfTheBaseScreen extends ContainerScreen<HeartOfTheBaseContainer> {
    HeartOfTheBaseTile tileEntity = (HeartOfTheBaseTile)menu.tileEntity.getLevel().getBlockEntity(menu.pos);
    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
            "textures/gui/heart_of_the_base_gui.png");

    public HeartOfTheBaseScreen(HeartOfTheBaseContainer heartOfTheBaseContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(heartOfTheBaseContainer, playerInventory, iTextComponent);
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
        if (tileEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.RUNESTONE_BLUE.get()) {
            this.blit(matrixStack, i + 103, j + 30, 192, 16, 27, 27);
        } else if (tileEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.RUNESTONE_PURPLE.get()) {
            this.blit(matrixStack, i + 95, j + 22, 184, 8, 43, 43);
        } else if (tileEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.RUNESTONE_RED.get()) {
            this.blit(matrixStack, i + 87, j + 14, 176, 0, 59, 59);
        }
    }
}
