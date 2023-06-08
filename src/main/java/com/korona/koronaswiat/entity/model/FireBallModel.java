package com.korona.koronaswiat.entity.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.korona.koronaswiat.entity.projectile.FireBallEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FireBallModel<T extends FireBallEntity> extends EntityModel<FireBallEntity> {
	private final ModelRenderer bb_main;

	public FireBallModel() {
		int textureWidth = 32;
		int textureHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(FireBallEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.x = x;
		modelRenderer.y = y;
		modelRenderer.z = z;
	}
}