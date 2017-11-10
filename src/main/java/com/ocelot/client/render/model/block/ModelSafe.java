// Date: 10/23/2017 9:33:10 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX
package com.ocelot.client.render.model.block;

/**
 * @author ZeuX
 */
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSafe extends ModelBase {
	// fields
	public ModelRenderer Shape1;
	public ModelRenderer Shape2;
	public ModelRenderer Shape3;
	public ModelRenderer Shape4;
	public ModelRenderer Shape5;
	public ModelRenderer Shape6;
	public ModelRenderer Shape7;
	public ModelRenderer Shape8;
	public ModelRenderer Shape9;
	public ModelRenderer lid;
	public ModelRenderer handle;

	public ModelSafe() {
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 48, 116);
		Shape1.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape1.setRotationPoint(-6F, 23F, -6F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 88, 123);
		Shape2.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape2.setRotationPoint(4F, 23F, 4F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 88, 116);
		Shape3.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape3.setRotationPoint(4F, 23F, -6F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 48, 122);
		Shape4.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape4.setRotationPoint(-6F, 23F, 4F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 48, 100);
		Shape5.addBox(0F, 0F, 0F, 12, 1, 12);
		Shape5.setRotationPoint(-6F, 22F, -6F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 33, 65);
		Shape6.addBox(0F, 0F, 0F, 1, 13, 12);
		Shape6.setRotationPoint(-6F, 9F, -6F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 90, 63);
		Shape7.addBox(0F, 0F, 0F, 1, 13, 12);
		Shape7.setRotationPoint(5F, 9F, -6F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 62, 60);
		Shape8.addBox(0F, 0F, 0F, 10, 13, 1);
		Shape8.setRotationPoint(-5F, 9F, 5F);
		Shape8.setTextureSize(128, 128);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 53, 44);
		Shape9.addBox(0F, 0F, 0F, 10, 1, 11);
		Shape9.setRotationPoint(-5F, 9F, -6F);
		Shape9.setTextureSize(128, 128);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		lid = new ModelRenderer(this, 62, 80);
		lid.addBox(0F, 0F, 0F, 10, 12, 1);
		lid.setRotationPoint(-5F, 10F, -6F);
		lid.setTextureSize(128, 128);
		lid.mirror = true;
		setRotation(lid, 0F, 0F, 0F);
		handle = new ModelRenderer(this, 74, 95);
		handle.addBox(6F, 5F, -1F, 2, 2, 1);
//		Shape11.setRotationPoint(-5F, -10F, -6F);
		handle.setTextureSize(128, 128);
		handle.mirror = true;
		setRotation(handle, 0F, 0F, 0F);
		lid.addChild(handle);
	}

	public void renderAll() {
		GlStateManager.translate(0.5, -0.5, 0.5);
		float f5 = 0.0625F;
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		lid.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}