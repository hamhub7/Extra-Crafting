package com.hamhub7.extracrafting.network;

import com.hamhub7.extracrafting.block.cauldron.TileEntityCauldron;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateCauldron implements IMessage
{
	private BlockPos pos;
	private ItemStack stack0;
	private ItemStack stack1;
	private long lastChangeTime;
	
	public PacketUpdateCauldron(BlockPos pos, ItemStack stack0, ItemStack stack1, long lastChangeTime)
	{
		this.pos = pos;
		this.stack0 = stack0;
		this.stack1 = stack1;
		this.lastChangeTime = lastChangeTime;
	}
	
	public PacketUpdateCauldron(TileEntityCauldron te) 
	{
		this(te.getPos(), te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.lastChangeTime);
	}
	
	public PacketUpdateCauldron() 
	{
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeItemStack(buf, stack0);
		ByteBufUtils.writeItemStack(buf, stack1);
		buf.writeLong(lastChangeTime);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		pos = BlockPos.fromLong(buf.readLong());
		stack0 = ByteBufUtils.readItemStack(buf);
		stack1 = ByteBufUtils.readItemStack(buf);
		lastChangeTime = buf.readLong();
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateCauldron, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateCauldron message, MessageContext ctx) 
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				TileEntityCauldron te = (TileEntityCauldron)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				te.inventory.setStackInSlot(0, message.stack0);
				te.inventory.setStackInSlot(1, message.stack1);
				te.lastChangeTime = message.lastChangeTime;
			});
			return null;
		}
	}
}
