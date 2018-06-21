package com.hamhub7.extracrafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab() 
	{
		super(ExtraCrafting.modid);
	}
	
	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(Items.APPLE);
	}
}
