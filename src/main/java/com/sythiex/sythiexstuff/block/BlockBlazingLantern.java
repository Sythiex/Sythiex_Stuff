package com.sythiex.sythiexstuff.block;

import com.sythiex.sythiexstuff.init.SythiexStuffBlocks;
import com.sythiex.sythiexstuff.proxy.CommonProxy;
import com.sythiex.sythiexstuff.tileentity.TileEntityBlazingLantern;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Better texture
public class BlockBlazingLantern extends Block
{
	public static final String NAME = "block_blazing_lantern";
	
	public BlockBlazingLantern()
	{
		super(Material.GLASS);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setCreativeTab(CommonProxy.tabSythiexStuff);
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.GLASS);
		this.setHardness(0.3F);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityBlazingLantern();
	}
	
	@Override
	public void breakBlock(World world, BlockPos blockPos, IBlockState state)
	{
		super.breakBlock(world, blockPos, state);
		
		for(int x = 0; x < 3; x++)
		{
			for(int i = -9; i <= 9; i++)
			{
				for(int j = -9; j <= 9; j++)
				{
					for(int k = -9; k <= 9; k++)
					{
						if((i % 3 == 0) && (j % 3 == 0) && (k % 3 == 0) && !(i == 0 && j == 0 && k == 0))
						{
							BlockPos lightPos = blockPos.add(i, j, k);
							IBlockState blockStateToReplace = world.getBlockState(lightPos);
							Block blockToReplace = blockStateToReplace.getBlock();
							if(blockToReplace == SythiexStuffBlocks.blockInvisibleLight)
							{
								world.setBlockToAir(lightPos);
							}
						}
					}
				}
			}
		}
	}
	
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
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState)
	{
		return EnumBlockRenderType.MODEL;
	}
}