package com.sythiex.sythiexstuff.init;

import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SythiexStuffConfig
{
	public static final String CATEGORY_BLOCKS = "Blocks";
	public static final String CATEGORY_ITEMS = "Items";
	public static final String CATEGORY_SETTINGS = "Settings";
	
	public static boolean addBlockInfiniteWater;
	public static boolean addBlockBlazingLantern;
	public static boolean addItemBuildWand;
	
	public static void loadConfig(FMLPreInitializationEvent event)
	{
		CommonProxy.logger.info("Loading Config");
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.setCategoryComment(CATEGORY_BLOCKS, "Enable/Disable Blocks");
		config.setCategoryRequiresMcRestart(CATEGORY_BLOCKS, true);
		config.setCategoryRequiresWorldRestart(CATEGORY_BLOCKS, true);
		
		config.setCategoryComment(CATEGORY_ITEMS, "Enable/Disable Items");
		config.setCategoryRequiresMcRestart(CATEGORY_ITEMS, true);
		config.setCategoryRequiresWorldRestart(CATEGORY_ITEMS, true);
		
		config.setCategoryComment(CATEGORY_SETTINGS, "Various Mod Settings");
		config.setCategoryRequiresMcRestart(CATEGORY_SETTINGS, true);
		config.setCategoryRequiresWorldRestart(CATEGORY_SETTINGS, true);
		
		config.load();
		
		addBlockBlazingLantern = config.getBoolean("Blazing Lantern", CATEGORY_BLOCKS, true, "");
		addBlockInfiniteWater = config.getBoolean("Ender Water Well", CATEGORY_BLOCKS, true, "");
		
		addItemBuildWand = config.getBoolean("Creative Build Wand", CATEGORY_ITEMS, true, "");
		
		config.save();
	}
}
