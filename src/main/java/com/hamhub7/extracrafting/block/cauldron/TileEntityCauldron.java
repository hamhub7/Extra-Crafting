package com.hamhub7.extracrafting.block.cauldron;

import javax.annotation.Nullable;

import com.hamhub7.extracrafting.ExtraCrafting;
import com.hamhub7.extracrafting.network.PacketRequestUpdateCauldron;
import com.hamhub7.extracrafting.network.PacketUpdateCauldron;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCauldron extends TileEntity
{
	public ItemStackHandler inventory = new ItemStackHandler(2)
	{
		@Override
		protected void onContentsChanged(int slot) 
		{
			if(!world.isRemote)
			{
				lastChangeTime = world.getTotalWorldTime();
				ExtraCrafting.network.sendToAllAround(new PacketUpdateCauldron(TileEntityCauldron.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
			}
		}
	};
	public long lastChangeTime;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
	}
	
	@Override
	public void onLoad() 
	{
		if(world.isRemote)
		{
			ExtraCrafting.network.sendToServer(new PacketRequestUpdateCauldron(this));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setLong("lastChangeTime", lastChangeTime);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		lastChangeTime = compound.getLong("lastChangeTime");
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) 
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) 
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}
	
	public boolean attemptToCraft()
	{
		ItemStack stack0 = inventory.getStackInSlot(0);
		ItemStack stack1 = inventory.getStackInSlot(1);
		
		if(stack0 == ItemStack.EMPTY || stack1 == ItemStack.EMPTY) return false;
		
		ItemStack result = CauldronRecipes.getInstance().getResult(stack0, stack1);
		
		if(result == ItemStack.EMPTY) return false;
		
		craftItems();
		return true;
	}
	
	public void craftItems()
	{
		ItemStack result = CauldronRecipes.getInstance().getResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1));
		inventory.extractItem(0, inventory.getStackInSlot(0).getCount(), false);
		inventory.extractItem(1, inventory.getStackInSlot(1).getCount(), false);
		
		if(!result.isEmpty())
		{
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), result);
			world.spawnEntity(item);
		}
	}
}