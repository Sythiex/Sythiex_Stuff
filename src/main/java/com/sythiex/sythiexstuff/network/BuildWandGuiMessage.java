package com.sythiex.sythiexstuff.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BuildWandGuiMessage implements IMessage
{
	private int buttonID;
	private boolean messageValid;
	
	public BuildWandGuiMessage(int buttonID)
	{
		this.buttonID = buttonID;
		this.messageValid = true;
	}
	
	public BuildWandGuiMessage()
	{
		this.messageValid = false;
	}
	
	public int getButtonID()
	{
		return buttonID;
	}
	
	public boolean isMessageValid()
	{
		return messageValid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			buttonID = buf.readInt();
		}
		catch(IndexOutOfBoundsException e)
		{
			System.err.println("Exception while reading BuildWandGuiMessage: " + e);
			return;
		}
		messageValid = true;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		if(!messageValid)
			return;
		buf.writeInt(buttonID);
	}
}