package com.sythiex.sythiexstuff;

import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SythiexStuffMod.MODID, name = SythiexStuffMod.NAME, version = SythiexStuffMod.VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class SythiexStuffMod
{
	@SidedProxy(clientSide = "com.sythiex.sythiexstuff.proxy.ClientProxy", serverSide = "com.sythiex.sythiexstuff.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String MODID = "sythiexstuff";
	public static final String NAME = "Sythiex's Stuff";
	public static final String VERSION = "0.1.0";
	
	@Instance(MODID)
	public static SythiexStuffMod instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}