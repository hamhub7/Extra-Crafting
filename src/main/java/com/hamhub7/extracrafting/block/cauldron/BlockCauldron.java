package com.hamhub7.extracrafting.block.cauldron;

import com.hamhub7.extracrafting.block.BlockTileEntity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockCauldron extends BlockTileEntity<TileEntityCauldron>
{
	public BlockCauldron() 
	{
		super(Material.IRON, "crafting_cauldron");
	}
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public Class<TileEntityCauldron> getTileEntityClass() 
	{
		return TileEntityCauldron.class;
	}
	
	@Override
	public TileEntityCauldron createTileEntity(World world, IBlockState state) 
	{
		return new TileEntityCauldron();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote)
		{
			ItemStack heldItem = player.getHeldItem(hand);
			TileEntityCauldron tile = (TileEntityCauldron)getTileEntity(world, pos);
			IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
			if(!player.isSneaking())
			{
				if(heldItem == ItemStack.EMPTY)
				{
					tile.attemptToCraft();
				}
				else
				{
					if(inventory.getStackInSlot(0) == ItemStack.EMPTY)
					{
						if(heldItem.getCount() == 1)
						{
							player.setHeldItem(hand, ItemStack.EMPTY);
						}
						else
						{
							player.setHeldItem(hand, new ItemStack(heldItem.getItem(), heldItem.getCount() - 1, heldItem.getMetadata()));
						}
						inventory.insertItem(0, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()), false);
					}
					else if(inventory.getStackInSlot(1) == ItemStack.EMPTY)
					{
						if(heldItem.getCount() == 1)
						{
							player.setHeldItem(hand, ItemStack.EMPTY);
						}
						else
						{
							player.setHeldItem(hand, new ItemStack(heldItem.getItem(), heldItem.getCount() - 1, heldItem.getMetadata()));
						}
						inventory.insertItem(1, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()), false);
					}
				}
			}
			else
			{
				dropInventory(world, pos);
				inventory.extractItem(0, inventory.getStackInSlot(0).getCount(), false);
				inventory.extractItem(1, inventory.getStackInSlot(1).getCount(), false);
			}
		}
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) 
	{
		dropInventory(world, pos);
		super.breakBlock(world, pos, state);
	}
	
	public void dropInventory(World world, BlockPos pos)
	{
		TileEntityCauldron tile = (TileEntityCauldron) getTileEntity(world, pos);
		IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
				world.spawnEntity(item);
			}
		}
	}
}
