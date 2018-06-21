package com.hamhub7.extracrafting.block.cauldron;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CauldronRecipes 
{
	private final static CauldronRecipes instance = new CauldronRecipes();
	private Table<ItemStack, ItemStack, ItemStack> recipes = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private Map<ItemStack, Integer> durations = Maps.<ItemStack, Integer>newHashMap();
	private Map<ItemStack, Integer> colors = Maps.<ItemStack, Integer>newHashMap();
	
	public static CauldronRecipes getInstance()
	{
		return instance;
	}
	
	public void addCauldronRecipe(ItemStack input1, ItemStack input2, ItemStack output, int duration, int color)
	{
		if(getResult(input1, input2) != ItemStack.EMPTY) return;
		recipes.put(input1, input2, output);
		durations.put(output, duration);
		colors.put(output, color);
	}
	
	public ItemStack getResult(ItemStack input1, ItemStack input2)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : recipes.columnMap().entrySet())
		{
			if(compareItemStacks(input1, (ItemStack)entry.getKey()))
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
				{
					if(compareItemStacks(input2, (ItemStack)ent.getKey()))
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : recipes.columnMap().entrySet())
		{
			if(compareItemStacks(input2, (ItemStack)entry.getKey()))
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
				{
					if(compareItemStacks(input1, (ItemStack)ent.getKey()))
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public int getDuration(ItemStack output)
	{
		for(Entry<ItemStack, Integer> entry : durations.entrySet())
		{
			if(this.compareItemStacks(output, entry.getKey()))
			{
				return (int)entry.getValue();
			}
		}
		return 0;
	}
	
	public int getColor(ItemStack output)
	{
		for(Entry<ItemStack, Integer> entry : colors.entrySet())
		{
			if(this.compareItemStacks(output, entry.getKey()))
			{
				return (int)entry.getValue();
			}
		}
		return 0;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		if(stack1 == ItemStack.EMPTY || stack2 == ItemStack.EMPTY || stack1 == null || stack2 == null)
		{
			return false;
		}
		else
		{
			return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == stack1.getMetadata());
		}
	}
}
