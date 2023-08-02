package com.korona.koronaswiat.entity.render;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.custom.projectile.FireBallEntity;
import com.korona.koronaswiat.item.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;


public class FireBallRenderer extends EntityRenderer<FireBallEntity> {
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    public static final ResourceLocation TEXTURE = new ResourceLocation(KoronaSwiat.MOD_ID, "textures/entity/projectiles/fire_ball.png");

    public FireBallRenderer(EntityRendererManager manager) {
        super(manager);
    }

    public void render(FireBallEntity fireBallEntity, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.mulPose(this.entityRenderDispatcher.cameraOrientation());
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        this.itemRenderer.renderStatic(new ItemStack(ModItems.FIRE_BALL.get()), ItemCameraTransforms.TransformType.GROUND, p_225623_6_, OverlayTexture.NO_OVERLAY, p_225623_4_, p_225623_5_);
        p_225623_4_.popPose();
        super.render(fireBallEntity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    public ResourceLocation getTextureLocation(FireBallEntity fireBall) {
        return TEXTURE;
    }
}

