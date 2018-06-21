package com.hamhub7.extracrafting.block.cauldron;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class TESRCauldron extends TileEntitySpecialRenderer<TileEntityCauldron>
{
	@Override
	public void render(TileEntityCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		for(int i = 0; i < te.inventory.getSlots(); i++)
		{
		ItemStack stack = te.inventory.getStackInSlot(i);
			if (!stack.isEmpty()) 
			{
				GlStateManager.enableRescaleNormal();
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
				GlStateManager.enableBlend();
				RenderHelper.enableStandardItemLighting();
				GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
				GlStateManager.pushMatrix();
				
				double offsetSin = Math.sin((te.getWorld().getTotalWorldTime() - te.lastChangeTime + partialTicks) / 8) / 5.0;
				double offsetCos = Math.cos((te.getWorld().getTotalWorldTime() - te.lastChangeTime + partialTicks) / 8) / 5.0;
				if(i == 0)
				{
					GlStateManager.translate(x + 0.5, y + offsetSin + 0.25, z + 0.5);
					GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);
				}
				else
				{
					GlStateManager.translate(x + 0.5, y - offsetSin + 0.25, z + 0.5);
					GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks + 10) * 4, 0, 1, 0);
				}
	
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
	
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
	
				GlStateManager.popMatrix();
				GlStateManager.disableRescaleNormal();
				GlStateManager.disableBlend();
			}
		}
	}
}
