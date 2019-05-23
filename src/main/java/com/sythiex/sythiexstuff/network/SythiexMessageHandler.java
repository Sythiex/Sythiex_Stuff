package com.sythiex.sythiexstuff.network;

import java.io.IOException;

import com.sythiex.sythiexstuff.item.ItemBuildWand;
import com.sythiex.sythiexstuff.item.ItemBuildWand.BuildWandShape;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SythiexMessageHandler implements IMessageHandler<BuildWandGuiMessage, IMessage>
{
	@Override
	public IMessage onMessage(final BuildWandGuiMessage message, MessageContext context)
	{
		if(context.side != Side.SERVER)
		{
			System.err.println("BuildWandGuiMessage received on wrong side: " + context.side);
			return null;
		}
		if(!message.isMessageValid())
		{
			System.err.println("BuildWandGuiMessage invalid: " + message.toString());
			return null;
		}
		
		final EntityPlayerMP sendingPlayer = context.getServerHandler().player;
		if(sendingPlayer == null)
		{
			System.err.println("EntityPlayerMP was null when BuildWandGuiMessage was received");
			return null;
		}
		
		final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
		playerWorldServer.addScheduledTask(new Runnable()
		{
			@Override
			public void run()
			{
				processMessage(message, sendingPlayer);
			}
		});
		
		return null;
	}
	
	void processMessage(BuildWandGuiMessage message, EntityPlayerMP player)
	{
		ItemStack stack = player.getHeldItemMainhand();
		if(stack != null)
		{
			if(stack.getItem() instanceof ItemBuildWand)
			{
				NBTTagCompound tag = stack.getTagCompound();
				switch(message.getButtonID())
				{
				// Shape
				case 0:
					int shape = tag.getInteger("shape") + 1;
					if(shape >= BuildWandShape.values().length)
						shape = 0;
					tag.setInteger("shape", shape);
					stack.setTagCompound(tag);
					return;
				// Hollow
				case 1:
					tag.setBoolean("isHollow", !tag.getBoolean("isHollow"));
					stack.setTagCompound(tag);
					return;
				// Reset
				case 2:
					tag.removeTag("pos1");
					tag.removeTag("pos2");
					tag.removeTag("pos3");
					player.sendMessage(new TextComponentTranslation("msg.buildwandreset.txt"));
					return;
				default:
					CommonProxy.logger.error("Server build wand button id mismatch");
				}
			}
		}
	}
}