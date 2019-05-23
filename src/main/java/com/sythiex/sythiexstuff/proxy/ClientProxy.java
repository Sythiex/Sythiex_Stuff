package com.sythiex.sythiexstuff.proxy;

import com.sythiex.sythiexstuff.SythiexStuffMod;
import com.sythiex.sythiexstuff.block.BlockBlazingLantern;
import com.sythiex.sythiexstuff.block.BlockInfiniteWater;
import com.sythiex.sythiexstuff.init.SythiexStuffBlocks;
import com.sythiex.sythiexstuff.init.SythiexStuffConfig;
import com.sythiex.sythiexstuff.init.SythiexStuffItems;
import com.sythiex.sythiexstuff.init.SythiexStuffNetwork;
import com.sythiex.sythiexstuff.item.ItemBuildWand;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		CommonProxy.logger.info("Loading Item Models");
		
		if(SythiexStuffConfig.addBlockInfiniteWater)
			ModelLoader.setCustomModelResourceLocation(SythiexStuffBlocks.itemBlockInfiniteWater, 0, new ModelResourceLocation(SythiexStuffMod.MODID + ":" + BlockInfiniteWater.NAME, "inventory"));
		else
			CommonProxy.logger.debug("Ender Water Well disabled in config, skipping item model load");
		
		if(SythiexStuffConfig.addBlockBlazingLantern)
			ModelLoader.setCustomModelResourceLocation(SythiexStuffBlocks.itemBlockBlazingLantern, 0, new ModelResourceLocation(SythiexStuffMod.MODID + ":" + BlockBlazingLantern.NAME, "inventory"));
		else
			CommonProxy.logger.debug("Blazing Lantern disabled in config, skipping item model load");
		
		if(SythiexStuffConfig.addItemBuildWand)
			ModelLoader.setCustomModelResourceLocation(SythiexStuffItems.itemBuildWand, 0, new ModelResourceLocation(SythiexStuffMod.MODID + ":" + ItemBuildWand.NAME, "inventory"));
		else
			CommonProxy.logger.debug("Creative Build Wand disabled in config, skipping item model load");
	}
	
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}
