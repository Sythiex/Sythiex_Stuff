package com.sythiex.sythiexstuff.init;

import com.sythiex.sythiexstuff.network.BuildWandGuiMessage;
import com.sythiex.sythiexstuff.network.SythiexMessageHandler;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class SythiexStuffNetwork
{
	public static SimpleNetworkWrapper networkWrapper;
	
	public static final String CHANNEL_NAME = "SythiexChannel";
	
	public static final byte BUILD_WAND_MESSAGE_ID = 1;
	
	public static void loadNetwork(FMLPreInitializationEvent event)
	{
		CommonProxy.logger.info("Registering Network");
		
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_NAME);
		networkWrapper.registerMessage(SythiexMessageHandler.class, BuildWandGuiMessage.class, BUILD_WAND_MESSAGE_ID, Side.SERVER);
	}
}
