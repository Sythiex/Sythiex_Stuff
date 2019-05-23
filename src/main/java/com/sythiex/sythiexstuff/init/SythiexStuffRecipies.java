package com.sythiex.sythiexstuff.init;

import com.sythiex.sythiexstuff.SythiexStuffMod;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.GameData;

public class SythiexStuffRecipies
{
	public static void loadRecipies(FMLInitializationEvent event)
	{
		CommonProxy.logger.info("Registering Recipies");
		
		ResourceLocation noGroup = new ResourceLocation("");
		
		// @formatter:off
		if(SythiexStuffConfig.addBlockInfiniteWater)
		{
			IRecipe blockInfiniteWaterRecipe = new ShapedOreRecipe(noGroup, new ItemStack(SythiexStuffBlocks.blockInfiniteWater), new Object[] {
					"OWO",
					"WEW",
					"OWO",
					'O', "obsidian",
					'W', new ItemStack(Items.WATER_BUCKET),
					'E', "enderpearl" });
			GameData.register_impl(blockInfiniteWaterRecipe.setRegistryName(new ResourceLocation(SythiexStuffMod.MODID + ":block_infinite_water_recipe")));
		}
		else
			CommonProxy.logger.debug("Ender Water Well disabled in config, skipping recipe registration");
		
		if(SythiexStuffConfig.addBlockBlazingLantern)
		{
			IRecipe blockBlazingLanternRecipe = new ShapedOreRecipe(noGroup, new ItemStack(SythiexStuffBlocks.blockBlazingLantern), new Object[] {
					"OBO",
					"BEB",
					"OBO",
					'O', "obsidian",
					'B', new ItemStack(Items.BLAZE_ROD),
					'E', new ItemStack(Items.ENDER_EYE)	});
			GameData.register_impl(blockBlazingLanternRecipe.setRegistryName(new ResourceLocation(SythiexStuffMod.MODID + ":block_blazing_lantern_recipe")));
		}
		else
			CommonProxy.logger.debug("Blazing Lantern disabled in config, skipping recipe registration");
		// @formatter:on
	}
}