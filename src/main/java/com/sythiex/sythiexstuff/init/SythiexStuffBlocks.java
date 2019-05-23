package com.sythiex.sythiexstuff.init;

import com.sythiex.sythiexstuff.block.BlockBlazingLantern;
import com.sythiex.sythiexstuff.block.BlockInfiniteWater;
import com.sythiex.sythiexstuff.block.BlockInvisibleLight;
import com.sythiex.sythiexstuff.proxy.CommonProxy;
import com.sythiex.sythiexstuff.tileentity.TileEntityBlazingLantern;
import com.sythiex.sythiexstuff.tileentity.TileEntityInfiniteWater;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SythiexStuffBlocks
{
	public static BlockInfiniteWater blockInfiniteWater;
	public static BlockBlazingLantern blockBlazingLantern;
	public static BlockInvisibleLight blockInvisibleLight;
	
	public static ItemBlock itemBlockInfiniteWater;
	public static ItemBlock itemBlockBlazingLantern;
	
	public static void loadBlocks(FMLPreInitializationEvent event)
	{
		CommonProxy.logger.info("Registering Blocks");
		
		if(SythiexStuffConfig.addBlockInfiniteWater)
		{
			blockInfiniteWater = new BlockInfiniteWater();
			ForgeRegistries.BLOCKS.register(blockInfiniteWater);
			
			itemBlockInfiniteWater = new ItemBlock(blockInfiniteWater);
			itemBlockInfiniteWater.setRegistryName(blockInfiniteWater.getRegistryName());
			ForgeRegistries.ITEMS.register(itemBlockInfiniteWater);
			
			GameRegistry.registerTileEntity(TileEntityInfiniteWater.class, "tile_entity_infinite_water");
		}
		else
			CommonProxy.logger.debug("Ender Water Well disabled in config, skipping block registration");
		
		if(SythiexStuffConfig.addBlockBlazingLantern)
		{
			blockBlazingLantern = new BlockBlazingLantern();
			ForgeRegistries.BLOCKS.register(blockBlazingLantern);
			
			itemBlockBlazingLantern = new ItemBlock(blockBlazingLantern);
			itemBlockBlazingLantern.setRegistryName(blockBlazingLantern.getRegistryName());
			ForgeRegistries.ITEMS.register(itemBlockBlazingLantern);
			
			GameRegistry.registerTileEntity(TileEntityBlazingLantern.class, "tile_entity_blazing_lantern");
			
			blockInvisibleLight = new BlockInvisibleLight();
			ForgeRegistries.BLOCKS.register(blockInvisibleLight);
		}
		else
			CommonProxy.logger.debug("Blazing Lantern disabled in config, skipping block registration");
	}
}