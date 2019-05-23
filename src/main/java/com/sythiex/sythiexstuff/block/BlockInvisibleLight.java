package com.sythiex.sythiexstuff.block;

import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;

public class BlockInvisibleLight extends BlockAir
{
	public static final String NAME = "block_invisible_light";
	
	public BlockInvisibleLight()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setLightLevel(1.0F);
	}
}