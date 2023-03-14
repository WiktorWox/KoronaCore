package com.korona.koronaswiat.screen;

import com.korona.koronaswiat.KoronaSwiat;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.Arrays;
import java.util.List;

public class DiscScreen extends Screen implements ITickable {
    Integer animateCounter = 0;
    Integer frame = 0;
    Minecraft minecraft = Minecraft.getInstance();
    Integer[] barFrames = new Integer[]{0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Integer[] discFrames = new Integer[]{54, 0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54};

    List<ITextProperties> disc0tooltip = Arrays.asList(
            new TranslationTextComponent("disc0.tooltip.title"),
            new TranslationTextComponent("disc0.tooltip.line1")
    );
    List<ITextProperties> disc1tooltip = Arrays.asList(
            new TranslationTextComponent("disc1.tooltip.title"),
            new TranslationTextComponent("disc1.tooltip.line1")
    );
    List<ITextProperties> disc2tooltip = Arrays.asList(
            new TranslationTextComponent("disc2.tooltip.title"),
            new TranslationTextComponent("disc2.tooltip.line1")
    );
    List<ITextProperties> disc3tooltip = Arrays.asList(
            new TranslationTextComponent("disc3.tooltip.title"),
            new TranslationTextComponent("disc3.tooltip.line1")
    );
    List<ITextProperties> disc4tooltip = Arrays.asList(
            new TranslationTextComponent("disc4.tooltip.title"),
            new TranslationTextComponent("disc4.tooltip.line1")
    );
    List<ITextProperties> disc5tooltip = Arrays.asList(
            new TranslationTextComponent("disc5.tooltip.title"),
            new TranslationTextComponent("disc5.tooltip.line1")
    );
    List<ITextProperties> disc6tooltip = Arrays.asList(
            new TranslationTextComponent("disc6.tooltip.title"),
            new TranslationTextComponent("disc6.tooltip.line1")
    );
    List<ITextProperties> disc7tooltip = Arrays.asList(
            new TranslationTextComponent("disc7.tooltip.title"),
            new TranslationTextComponent("disc7.tooltip.line1")
    );
    List<ITextProperties> disc8tooltip = Arrays.asList(
            new TranslationTextComponent("disc8.tooltip.title"),
            new TranslationTextComponent("disc8.tooltip.line1")
    );
    List<ITextProperties> disc9tooltip = Arrays.asList(
            new TranslationTextComponent("disc9.tooltip.title"),
            new TranslationTextComponent("disc9.tooltip.line1")
    );
    List<ITextProperties> disc10tooltip = Arrays.asList(
            new TranslationTextComponent("disc10.tooltip.title"),
            new TranslationTextComponent("disc10.tooltip.line1")
    );
    List<ITextProperties> disc11tooltip = Arrays.asList(
            new TranslationTextComponent("disc11.tooltip.title"),
            new TranslationTextComponent("disc11.tooltip.line1")
    );
    List<ITextProperties> disc12tooltip = Arrays.asList(
            new TranslationTextComponent("disc12.tooltip.title"),
            new TranslationTextComponent("disc12.tooltip.line1")
    );
    List<ITextProperties>[] discTooltips = new List[]{
            disc0tooltip,
            disc1tooltip,
            disc2tooltip,
            disc3tooltip,
            disc4tooltip,
            disc5tooltip,
            disc6tooltip,
            disc7tooltip,
            disc8tooltip,
            disc9tooltip,
            disc10tooltip,
            disc11tooltip,
            disc12tooltip
    };
    static final int discsXPosition = 136;
    static final int discsYPosition = 98;
    private final ResourceLocation GUI = new ResourceLocation(KoronaSwiat.MOD_ID,
            "textures/gui/disc_gui.png");

    FontRenderer fontRenderer = minecraft.font;

    public DiscScreen(ITextComponent iTextComponent) {
        super(iTextComponent);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 176) / 2;
        int j = (this.height - 166) / 2;
        this.width = this.minecraft.getWindow().getGuiScaledWidth();
        this.height = this.minecraft.getWindow().getGuiScaledHeight();
        this.renderBackground(matrixStack, i, j);
        this.renderText(matrixStack, minecraft.player, i, j);
        this.renderBar(matrixStack, i, j);
        this.renderDiscs(matrixStack, i, j);
        this.renderDiscTooltip(matrixStack, mouseX, mouseY, i, j);

    }

    protected void renderBackground(MatrixStack matrixStack, int posX, int posY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);

        this.blit(matrixStack, posX, posY, 0, 0, 176, 166);
    }

    protected void renderDiscTooltip(MatrixStack matrixStack, int mouseX, int mouseY, int posX, int posY) {
        int x = mouseX - posX - discsXPosition;
        int y = mouseY - posY - discsYPosition;
        int renderTooltip = 13;
        for (int i = 0; i <= 12; i++) {
            int typeOffset = i * 6;
            if (0 <= x && x <= 29) {
                if (0 - typeOffset <= y && y <= 6 - typeOffset) {
                    renderTooltip = i;
                }
            }
        }
        if (renderTooltip != 13) {
            List<ITextProperties> tooltip = discTooltips[renderTooltip];
            GuiUtils.drawHoveringText(matrixStack, tooltip, mouseX, mouseY, width, height, -1, fontRenderer);
        }
    }

    protected void renderBar(MatrixStack matrixStack, int posX, int posY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);

        this.blit(matrixStack, posX + 149, posY + 22, 250 + barFrames[frame], 0, 3, 86);
    }

    protected void renderDiscs(MatrixStack matrixStack, int posX, int posY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        String[] discsData = Minecraft.getInstance().player.getPersistentData().getString("discsFound").split("_");
        this.minecraft.getTextureManager().bind(GUI);
        for (int i = 0; i < discsData.length; i++) {
            if (!discsData[i].equals("null")) {
                Integer typeOffset = i * 6;
                Integer materialOffset;
                if (discsData[i].equals("stone")) {
                    materialOffset = 29;
                } else {
                    materialOffset = 0;
                }
                if (frame >= 10 || frame <= 0) {
                    this.blit(matrixStack, posX + discsXPosition, posY + discsYPosition - typeOffset, 176 + materialOffset, 6 + discFrames[frame] + typeOffset, 29, 6);
                } else {
                    if (i >= 9) {
                        this.blit(matrixStack, posX + discsXPosition, posY + discsYPosition - typeOffset, 176 + materialOffset, 6 + discFrames[frame] + 132, 29, 6);
                    } else {
                        this.blit(matrixStack, posX + discsXPosition, posY + discsYPosition - typeOffset, 176 + materialOffset, 6 + discFrames[frame], 29, 6);
                    }
                }
            }
        }
    }

    protected void renderText(MatrixStack matrixStack, PlayerEntity player, int posX, int posY) {
        String text = "Dyski";
        this.drawString(matrixStack, minecraft.font, text, posX + 134, posY + 10, 0x2d241d);
    }
    public void tick() {
        if (animateCounter == 5) {
            animateCounter = 0;
            nextFrame();

        }
        animateCounter++;
    }
    public void nextFrame() {
        if (frame == 20) {
            frame = 0;
        }
        frame++;
    }
}