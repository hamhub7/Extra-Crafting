package com.hamhub7.extracrafting.config;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.hamhub7.extracrafting.ExtraCrafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class Config 
{
	private static final String CATEGORY_ENABLING = "enabling";
	
	public static boolean cauldronEnabled = true;
	
	public static void readConfig()
	{
		Configuration cfg = ExtraCrafting.config;
		try
		{
			cfg.load();
			initEnablingConfig(cfg);
		}
		catch (Exception e1)
		{
			ExtraCrafting.logger.log(Level.ERROR, "Problem loading config file", e1);
		}
		finally
		{
			if(cfg.hasChanged())
			{
				cfg.save();
			}
		}
	}
	
	private static void initEnablingConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_ENABLING, "Enable or disable the different crafting devices");
		cauldronEnabled = cfg.getBoolean("cauldronEnabled", CATEGORY_ENABLING, true, "Set to false to disable the cauldron");
	}
}
