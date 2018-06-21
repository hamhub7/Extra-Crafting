package com.hamhub7.extracrafting.network;

import com.hamhub7.extracrafting.block.cauldron.TileEntityCauldron;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateCauldron implements IMessage
{
	private BlockPos pos;
	private int dimension;
	
	public PacketRequestUpdateCauldron(BlockPos pos, int dimension)
	{
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public PacketRequestUpdateCauldron(TileEntityCauldron te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public PacketRequestUpdateCauldron() 
	{
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeLong(pos.toLong());
		buf.writeInt(dimension);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		pos = BlockPos.fromLong(buf.readLong());
		dimension = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<PacketRequestUpdateCauldron, PacketUpdateCauldron> 
	{
		@Override
		public PacketUpdateCauldron onMessage(PacketRequestUpdateCauldron message, MessageContext ctx) 
		{
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
			TileEntityCauldron te = (TileEntityCauldron)world.getTileEntity(message.pos);
			if (te != null) 
			{
				return new PacketUpdateCauldron(te);
			} 
			else 
			{
				return null;
			}
		}
	
	}
}
