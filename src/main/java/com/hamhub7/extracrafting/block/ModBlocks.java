package com.hamhub7.extracrafting.block;

import com.hamhub7.extracrafting.block.cauldron.BlockCauldron;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks 
{
	public static BlockCauldron cauldron = new BlockCauldron();
	
	public static void register(IForgeRegistry<Block> registry)
	{
		GameRegistry.registerTileEntity(cauldron.getTileEntityClass(), cauldron.getRegistryName().toString());
		registry.registerAll
		(
			cauldron
		);
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		registry.registerAll
		(
			cauldron.createItemBlock()
		);
	}
	
	public static void registerModels()
	{
		cauldron.registerItemModel(Item.getItemFromBlock(cauldron));
	}
}
