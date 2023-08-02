// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.korona.koronaswiat.entity.model;

import com.korona.koronaswiat.entity.custom.RaidfarmingEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class RaidfarmingModel extends EntityModel<RaidfarmingEntity> {
	private final ModelRenderer body;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private final ModelRenderer arms;
	private final ModelRenderer arms_r1;
	private final ModelRenderer head;

	public RaidfarmingModel() {
		texWidth = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		body.texOffs(0, 16).addBox(-4.0F, -17.0F, -2.0F, 8.0F, 9.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(LeftLeg);
		LeftLeg.texOffs(12, 39).addBox(0.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(RightLeg);
		RightLeg.texOffs(32, 35).addBox(-4.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		arms = new ModelRenderer(this);
		arms.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(arms);


		arms_r1 = new ModelRenderer(this);
		arms_r1.setPos(0.0F, 0.0F, 0.0F);
		arms.addChild(arms_r1);
		setRotationAngle(arms_r1, -0.7854F, 0.0F, 0.0F);
		arms_r1.texOffs(20, 27).addBox(4.0F, -13.0F, -13.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
		arms_r1.texOffs(0, 29).addBox(-8.0F, -13.0F, -13.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
		arms_r1.texOffs(24, 0).addBox(-4.0F, -9.0F, -13.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(head);
		head.texOffs(32, 48).addBox(-4.0F, -25.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
		head.texOffs(0, 0).addBox(-1.0F, -20.0F, -6.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		head.texOffs(24, 16).addBox(-5.0F, -26.0F, -5.0F, 10.0F, 9.0F, 2.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-4.0F, -25.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(RaidfarmingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.RightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.LeftLeg.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}