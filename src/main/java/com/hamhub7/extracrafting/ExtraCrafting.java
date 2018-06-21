package com.hamhub7.extracrafting;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.hamhub7.extracrafting.block.ModBlocks;
import com.hamhub7.extracrafting.block.cauldron.CauldronRecipes;
import com.hamhub7.extracrafting.config.Config;
import com.hamhub7.extracrafting.integration.CraftweakerPlugin;
import com.hamhub7.extracrafting.network.PacketRequestUpdateCauldron;
import com.hamhub7.extracrafting.network.PacketUpdateCauldron;
import com.hamhub7.extracrafting.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ExtraCrafting.modid, name = ExtraCrafting.name, version = ExtraCrafting.version)
public class ExtraCrafting
{
    public static final String modid = "extracrafting";
    public static final String name = "Extra Crafting";
    public static final String version = "1.0.0";
    
    public static Configuration config;

    @Mod.Instance(modid)
    public static ExtraCrafting instance;
    public static Logger logger;
    
    @SidedProxy(serverSide = "com.hamhub7.extracrafting.proxy.CommonProxy", clientSide = "com.hamhub7.extracrafting.proxy.ClientProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network;
    public static final CreativeTab creativeTab = new CreativeTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	System.out.println(name + " is loading my dudes!");
        logger = event.getModLog();
    	proxy.registerRenderers();
    	File directory = event.getModConfigurationDirectory();
    	config = new Configuration(new File(directory.getPath(), "extracrafting.cfg"));
    	Config.readConfig();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(modid);
    	network.registerMessage(new PacketUpdateCauldron.Handler(), PacketUpdateCauldron.class, 0, Side.CLIENT);
    	network.registerMessage(new PacketRequestUpdateCauldron.Handler(), PacketRequestUpdateCauldron.class, 1, Side.SERVER);
    	CraftweakerPlugin.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    }
    
    @Mod.EventHandler
    public void postInit(FMLInitializationEvent event)
    {
    	CraftweakerPlugin.apply();
    	if(config.hasChanged())
    	{
    		config.save();
    	}
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	
    }
    
    @Mod.EventBusSubscriber
    public static class RegistrationHandler
    {
    	@SubscribeEvent
    	public static void registerBlocks(RegistryEvent.Register<Block> event)
    	{
    		ModBlocks.register(event.getRegistry());
    	}
    	
    	@SubscribeEvent
    	public static void registerItems(RegistryEvent.Register<Item> event)
    	{
    		ModBlocks.registerItemBlocks(event.getRegistry());
    	}
    	
    	@SubscribeEvent
    	public static void registerModels(ModelRegistryEvent event)
    	{
    		ModBlocks.registerModels();
    	}
    }
}
