package com.sythiex.sythiexstuff.tileentity;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.sythiex.sythiexstuff.init.SythiexStuffBlocks;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileEntityBlazingLantern extends TileEntity implements ITickable
{
	private int timer = 0;
	private final int TIMER_MAX_VALUE = 200; // update every 10 sec
	
	@Override
	public void update()
	{
		if(!this.getWorld().isRemote)
		{
			if(timer <= 0)
			{
				timer = TIMER_MAX_VALUE;
				for(int i = -9; i <= 9; i++)
				{
					for(int j = -9; j <= 9; j++)
					{
						for(int k = -9; k <= 9; k++)
						{
							if((i % 3 == 0) && (j % 3 == 0) && (k % 3 == 0) && !(i == 0 && j == 0 && k == 0))
							{
								BlockPos lightPos = this.getPos().add(i, j, k);
								IBlockState blockStateToReplace = this.getWorld().getBlockState(lightPos);
								Block blockToReplace = blockStateToReplace.getBlock();
								if(blockToReplace == Blocks.AIR)
								{
									this.getWorld().setBlockState(lightPos, SythiexStuffBlocks.blockInvisibleLight.getDefaultState());
								}
							}
						}
					}
				}
			}
			timer--;
		}
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