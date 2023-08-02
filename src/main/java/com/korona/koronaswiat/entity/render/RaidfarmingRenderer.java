package com.korona.koronaswiat.entity.render;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.custom.RaidfarmingEntity;
import com.korona.koronaswiat.entity.model.RaidfarmingModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.ResourceLocation;

public class RaidfarmingRenderer extends MobRenderer<RaidfarmingEntity, RaidfarmingModel> {
    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(KoronaSwiat.MOD_ID, "textures/entity/raidfarming.png");

    public RaidfarmingRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RaidfarmingModel(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(RaidfarmingEntity entity) {
        return TEXTURE;
    }
}
