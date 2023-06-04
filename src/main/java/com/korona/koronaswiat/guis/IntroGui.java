//package com.korona.koronaswiat.guis;
//
//import com.korona.koronaswiat.KoronaSwiat;
//import com.korona.koronaswiat.item.ModItems;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.AbstractGui;
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.tileentity.ITickableTileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.event.RenderGameOverlayEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//import org.apache.logging.log4j.core.StringLayout;
//
//import java.util.Set;
//
//@OnlyIn(Dist.CLIENT)
//public class IntroGui extends AbstractGui  {
//    Minecraft minecraft = Minecraft.getInstance();
//    ItemStackHandler inventoryHandler;
//    int screenWidth;
//    int screenHeight;
//    int counter = 0;
//    ItemRenderer itemRenderer = minecraft.getItemRenderer();
//    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
//            "textures/gui/introduction/meadow.png");
//    private Set<ResourceLocation> gui_meadow;
//    @SubscribeEvent
//    public void render(RenderGameOverlayEvent event) {
//        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
//            PlayerEntity player = minecraft.player;
//            if (player.getMainHandItem().getItem() == ModItems.WAND.get()) {
//                int i = (screenWidth / 2) - 25;
//                int j = screenHeight - 23;
//                this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
//                this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
//                this.renderBg(event.getMatrixStack(), i, j);
//            }
//        }
//    }
//    protected void renderBg(MatrixStack matrixStack, int posX, int posY) {
//        RenderSystem.color4f(1f, 1f, 1f, 1f);
//        this.minecraft.getTextureManager().bind(GUI);
//        this.blit(matrixStack, posX, 0, 0, 0, 250, 200);
//    }
//
////    @Override
////    public void tick() {
////        counter++;
////        if (counter == 10) {
////            counter = 0;
////        }
////    }
//}
