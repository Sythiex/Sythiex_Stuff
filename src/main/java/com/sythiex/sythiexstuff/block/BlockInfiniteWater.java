package com.sythiex.sythiexstuff.block;

import com.sythiex.sythiexstuff.proxy.CommonProxy;
import com.sythiex.sythiexstuff.tileentity.TileEntityInfiniteWater;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Better texture, internal starfield rendering
public class BlockInfiniteWater extends Block
{
	public static final String NAME = "block_infinite_water";
	
	public BlockInfiniteWater()
	{
		super(Material.GLASS);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setCreativeTab(CommonProxy.tabSythiexStuff);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityInfiniteWater();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		TileEntity tile = world.getTileEntity(blockPos);
		if(tile != null)
		{
			IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(fluidHandler != null)
			{
				return FluidUtil.interactWithFluidHandler(player, hand, fluidHandler);
			}
		}
		return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState)
	{
		return EnumBlockRenderType.MODEL;
	}
}