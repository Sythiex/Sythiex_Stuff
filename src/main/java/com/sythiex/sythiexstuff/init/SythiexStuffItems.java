package com.sythiex.sythiexstuff.init;

import com.sythiex.sythiexstuff.item.ItemBuildWand;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SythiexStuffItems
{	
	public static ItemBuildWand itemBuildWand;
	
	public static void loadItems(FMLPreInitializationEvent event)
	{
		CommonProxy.logger.info("Registering Items");
		
		if(SythiexStuffConfig.addItemBuildWand)
		{
			itemBuildWand = new ItemBuildWand();
			ForgeRegistries.ITEMS.register(itemBuildWand);
		}
		else
			CommonProxy.logger.debug("Creative Build Wand disabled in config, skipping item registration");
	}
}