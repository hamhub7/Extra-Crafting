package com.hamhub7.extracrafting.integration;

import static com.hamhub7.extracrafting.integration.ZenCauldron.NAME;

import java.util.List;

import com.hamhub7.extracrafting.ModUtils;
import com.hamhub7.extracrafting.block.cauldron.CauldronRecipes;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(NAME)
public class ZenCauldron 
{
	public static final String NAME = "mods.extracrafting.Cauldron";
	
	@ZenMethod
	public static void addRecipe(IItemStack input1, IItemStack input2, IItemStack output, int duration, int color)
	{
		CraftweakerPlugin.LATE_ADDITIONS.add(new Add(InputHelper.toStack(input1), InputHelper.toStack(input2), InputHelper.toStack(output), duration, color));
	}
	
	private static class Add extends BaseUndoable
	{
		private final ItemStack input1;
		private final ItemStack input2;
		private final ItemStack output;
		private final int duration;
		private final int color;
		
		public Add(ItemStack input1, ItemStack input2, ItemStack output, int duration, int color) 
		{
			super(NAME);
			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
			this.duration = duration;
			this.color = color;
		}
		
		@Override
		public void apply() 
		{
			if(input1.getCount() > 1)
			{
				ModUtils.logCrafttweakerError("Bro your input 1 is more that 1 count...");
				return;
			}
			if(input2.getCount() > 1)
			{
				ModUtils.logCrafttweakerError("Bro your input 2 is more that 1 count...");
				return;
			}
			CauldronRecipes.getInstance().addCauldronRecipe(this.input1, this.input2, this.output, this.duration, this.color);
		}
		
		@Override
		protected String getRecipeInfo() 
		{
			return (this.output != null && !this.output.isEmpty()) ? LogHelper.getStackDescription(this.output) : "EMPTY";
		}
	}
}
