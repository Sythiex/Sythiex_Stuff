package com.sythiex.sythiexstuff.proxy;

import org.apache.logging.log4j.Logger;

import com.sythiex.sythiexstuff.SythiexStuffMod;
import com.sythiex.sythiexstuff.gui.SythiexStuffGuiHandler;
import com.sythiex.sythiexstuff.init.SythiexStuffBlocks;
import com.sythiex.sythiexstuff.init.SythiexStuffConfig;
import com.sythiex.sythiexstuff.init.SythiexStuffItems;
import com.sythiex.sythiexstuff.init.SythiexStuffNetwork;
import com.sythiex.sythiexstuff.init.SythiexStuffRecipies;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy
{
	public static Logger logger;
	public static CreativeTabs tabSythiexStuff;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		logger.info("Preinitializing");
		
		tabSythiexStuff = new CreativeTabs("tab_sythiex_stuff")
		{
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem()
			{
				return new ItemStack(SythiexStuffBlocks.blockInfiniteWater);
			}
		};
		
		SythiexStuffConfig.loadConfig(event);
		SythiexStuffBlocks.loadBlocks(event);
		SythiexStuffItems.loadItems(event);
		SythiexStuffNetwork.loadNetwork(event);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(SythiexStuffMod.instance, new SythiexStuffGuiHandler());
	}
	
	public void init(FMLInitializationEvent event)
	{
		logger.info("Initializing");
		
		SythiexStuffRecipies.loadRecipies(event);
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		logger.info("Postinitializing");
	}
}
