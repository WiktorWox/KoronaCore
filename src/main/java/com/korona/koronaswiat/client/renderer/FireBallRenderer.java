package com.korona.koronaswiat.client.renderer;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.projectile.FireBallEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;


public class FireBallRenderer extends ArrowRenderer<FireBallEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(KoronaSwiat.MOD_ID, "textures/entity/projectiles/arrow.png");

    public FireBallRenderer(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getTextureLocation(FireBallEntity fireBall) {
        return TEXTURE;
    }
}


