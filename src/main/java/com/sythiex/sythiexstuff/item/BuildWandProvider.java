package com.sythiex.sythiexstuff.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BuildWandProvider implements ICapabilityProvider, ICapabilitySerializable
{
	private BuildWandItemStackHandler inventory;
	
	public BuildWandProvider()
	{
		inventory = new BuildWandItemStackHandler(1);
	}
    
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? true : false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? (T) inventory : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return inventory.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		inventory.deserializeNBT((NBTTagCompound) nbt);
	}
}