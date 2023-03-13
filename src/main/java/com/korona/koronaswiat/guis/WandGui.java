package com.korona.koronaswiat.guis;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.item.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@OnlyIn(Dist.CLIENT)
public class WandGui extends AbstractGui {
    Minecraft minecraft = Minecraft.getInstance();
    ItemStackHandler inventoryHandler;
    int screenWidth;
    int screenHeight;
    ItemRenderer itemRenderer = minecraft.getItemRenderer();
    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
            "textures/gui/wand_hotbar_gui.png");

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            PlayerEntity player = minecraft.player;
            ItemStack itemStack = player.getMainHandItem();
            if (player.getMainHandItem().getItem() == ModItems.WAND.get()) {
                int i = (screenWidth / 2) + 99;
                int j = screenHeight - 23;
                this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
                this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
                this.renderBg(event.getMatrixStack(), i, j);
                this.renderItems(player, itemStack);
                this.renderSelect(itemStack, event.getMatrixStack(), i, j);
            }
        }
    }
    protected void renderBg(MatrixStack matrixStack, int posX, int posY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);

        this.blit(matrixStack, posX, posY, 0, 0, 63, 23);
    }

    protected void renderSelect(ItemStack itemStack, MatrixStack matrixStack, int posX, int posY) {
        this.minecraft.getTextureManager().bind(GUI);
        CompoundNBT nbtData;
        if (itemStack.hasTag()) {
            nbtData = itemStack.getTag();
        } else {
            nbtData = new CompoundNBT();
        }
        switch (nbtData.getInt("useItem")) {
            case 0:
                blit(matrixStack, posX + 0, posY, 64, 0, 24, 24);
                break;
            case 1:
                blit(matrixStack, posX + 20, posY, 64, 0, 24, 24);
                break;
            case 2:
                blit(matrixStack, posX + 40, posY, 64, 0, 24, 24);
                break;
        }
    }
    protected void renderItems(PlayerEntity player, ItemStack itemStack) {
        int k1 = screenHeight - 16 - 3;
        ItemStack slot0 = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler()).getStackInSlot(0);
        ItemStack slot1 = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler()).getStackInSlot(1);
        ItemStack slot2 = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler()).getStackInSlot(2);
        renderSlot((screenWidth / 2) + 100 + 3, k1, player, slot0);
        renderSlot((screenWidth / 2) + 100 + 3 + 20, k1, player, slot1);
        renderSlot((screenWidth / 2) + 100 + 3 + 40, k1, player, slot2);
    }
    protected void renderSlot(int posX, int posY, PlayerEntity player, ItemStack slot) {
        if (!slot.isEmpty()) {
            minecraft.getItemRenderer().renderAndDecorateItem(player, slot, posX, posY);
//            itemRenderer.renderGuiItemDecorations(this.minecraft.font, itemStack, posX, posY);
        }
    }
}
