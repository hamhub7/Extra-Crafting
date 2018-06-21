package com.hamhub7.extracrafting.integration;

import java.util.LinkedList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

public class CraftweakerPlugin 
{
	public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
	public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();
	
	public static void init()
	{
		CraftTweakerAPI.registerClass(ZenCauldron.class);
	}
	
	public static void apply()
	{
		LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
		LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
	}
}
