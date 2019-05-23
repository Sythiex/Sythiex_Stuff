package com.sythiex.sythiexstuff.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class BuildWandItemStackHandler extends ItemStackHandler
{
	public BuildWandItemStackHandler(int size)
	{
		stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}
	
	@Override
	public int getSlotLimit(int slot)
	{
		return 1;
	}
}