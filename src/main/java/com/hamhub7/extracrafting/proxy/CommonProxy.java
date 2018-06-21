package com.hamhub7.extracrafting.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id)
	{
		
	}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id)
	{
		
	}
	
	public void registerRenderers() 
	{
		
	}
	
	public String localize(String unlocalized, Object... args)
	{
		return I18n.translateToLocalFormatted(unlocalized, args);
	}
}
