package com.korona.koronaswiat.entity.render;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.custom.projectile.FireBallEntity;
import com.korona.koronaswiat.entity.custom.projectile.SpearEntity;
import com.korona.koronaswiat.item.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;


public class SpearRenderer extends EntityRenderer<SpearEntity> {
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    public static final ResourceLocation TEXTURE = new ResourceLocation(KoronaSwiat.MOD_ID, "textures/entity/projectiles/spear.png");

    public SpearRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(SpearEntity p_110775_1_) {
        return TEXTURE;
    }

    public void render(SpearEntity spear, float float1, float float2, MatrixStack p_225623_4_, IRenderTypeBuffer iRenderTypeBuffer, int p_225623_6_) {
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(float1, spear.yRotO, spear.yRot) - 90.0F));
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(float1, spear.xRotO, spear.xRot)));
        int i = 0;
        float f = 0.0F;
        float f1 = 0.5F;
        float f2 = 0.0F;
        float f3 = 0.15625F;
        float f4 = 0.0F;
        float f5 = 0.15625F;
        float f6 = 0.15625F;
        float f7 = 0.3125F;
        float f8 = 0.05625F;
        float f9 = 0.05625F;
        if (f9 > 0.0F) {
            float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
            p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(f10));
        }

        p_225623_4_.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        p_225623_4_.scale(0.05625F, 0.05625F, 0.05625F);
        p_225623_4_.translate(-4.0D, 0.0D, 0.0D);
        IVertexBuilder ivertexbuilder = iRenderTypeBuffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(spear)));
        MatrixStack.Entry matrixstack$entry = p_225623_4_.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_225623_6_);
        this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_225623_6_);

        for(int j = 0; j < 4; ++j) {
            p_225623_4_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            this.vertex(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_225623_6_);
            this.vertex(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_225623_6_);
            this.vertex(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_225623_6_);
            this.vertex(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_225623_6_);
        }
        p_225623_4_.pushPose();
        p_225623_4_.mulPose(this.entityRenderDispatcher.cameraOrientation());
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        this.itemRenderer.renderStatic(new ItemStack(ModItems.SPEAR.get()), ItemCameraTransforms.TransformType.GROUND, p_225623_6_, OverlayTexture.NO_OVERLAY, p_225623_4_, iRenderTypeBuffer);
        p_225623_4_.popPose();
        super.render(spear, float1, float2, p_225623_4_, iRenderTypeBuffer, p_225623_6_);
    }
    public void vertex(Matrix4f p_229039_1_, Matrix3f p_229039_2_, IVertexBuilder p_229039_3_, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) {
        p_229039_3_.vertex(p_229039_1_, (float)p_229039_4_, (float)p_229039_5_, (float)p_229039_6_).color(255, 255, 255, 255).uv(p_229039_7_, p_229039_8_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229039_12_).normal(p_229039_2_, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
    }
}


