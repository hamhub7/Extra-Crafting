package com.hamhub7.extracrafting;

import crafttweaker.CraftTweakerAPI;

public class ModUtils 
{
	public static void logCrafttweakerError(String message)
	{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		for(StackTraceElement element : stackTrace)
		{
			String methodName = element.getMethodName();
			
			if("__script__".equals(methodName))
			{
				CraftTweakerAPI.logError(message + " (" + element.getFileName() + ":" + element.getLineNumber() + ")");
				break;
			}
		}
	}
	
	
}
