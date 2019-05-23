package com.sythiex.sythiexstuff.tileentity;

import javax.annotation.Nullable;

import com.sythiex.sythiexstuff.fluid.FluidTankWithTile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.TileFluidHandler;

public class TileEntityInfiniteWater extends TileFluidHandler implements ITickable
{
	public static final int CAPACITY = 1000000;
	
	public TileEntityInfiniteWater()
	{
		tank = new FluidTankWithTile(this, CAPACITY);
	}
	
	@Override
	public void update()
	{
		tank.setFluid(new FluidStack(FluidRegistry.WATER, this.CAPACITY));
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}
}