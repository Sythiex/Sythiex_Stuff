package com.sythiex.sythiexstuff.item;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

import com.sythiex.sythiexstuff.SythiexStuffMod;
import com.sythiex.sythiexstuff.gui.SythiexStuffGuiHandler;
import com.sythiex.sythiexstuff.proxy.CommonProxy;
import com.sythiex.sythiexstuff.util.Math3d;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;

// Needs Dome, Rhombus, and Triangle
// Needs preview rendering
public class ItemBuildWand extends Item
{
	public static final String NAME = "item_build_wand";
	
	public ItemBuildWand()
	{
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setCreativeTab(CommonProxy.tabSythiexStuff);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote)
		{
			NBTTagCompound tag = itemStack.getTagCompound();
			if(tag == null)
			{
				tag = new NBTTagCompound();
				tag.setInteger("shape", 0);
				tag.setBoolean("isHollow", false);
				itemStack.setTagCompound(tag);
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(player.isSneaking())
		{
			player.openGui(SythiexStuffMod.instance, SythiexStuffGuiHandler.GUI_BUILD_WAND_ID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!player.isSneaking())
		{
			ItemStack wand = player.getHeldItem(hand);
			NBTTagCompound tag = wand.getTagCompound();
			
			// If missing pos1, set it and return
			if(!tag.hasKey("pos1"))
			{
				tag.setLong("pos1", pos.toLong());
				CommonProxy.logger.info("Position 1 set: " + pos.toString());
				wand.setTagCompound(tag);
				return EnumActionResult.PASS;
			}
			// If missing pos2, set it. If shape needs 3 points, return
			if(!tag.hasKey("pos2"))
			{
				tag.setLong("pos2", pos.toLong());
				CommonProxy.logger.info("Position 2 set: " + pos.toString());
				
				if(tag.getInteger("shape") == BuildWandShape.CYLINDER.ordinal() || tag.getInteger("shape") == BuildWandShape.RHOMBUS.ordinal() || tag.getInteger("shape") == BuildWandShape.TRIANGLE.ordinal())
				{
					wand.setTagCompound(tag);
					return EnumActionResult.PASS;
				}
			}
			// If missing pos2, set it
			if(!tag.hasKey("pos3") && (tag.getInteger("shape") == BuildWandShape.CYLINDER.ordinal() || tag.getInteger("shape") == BuildWandShape.RHOMBUS.ordinal() || tag.getInteger("shape") == BuildWandShape.TRIANGLE.ordinal()))
			{
				tag.setLong("pos3", pos.toLong());
				CommonProxy.logger.info("Position 3 set: " + pos.toString());
			}
			
			wand.setTagCompound(tag);
			
			switch(tag.getInteger("shape"))
			{
			case 0:
				createRectPrism(wand, player, world, hand, facing, hitX, hitY, hitZ);
				break;
			case 1:
				createCylinder(wand, player, world, hand, facing, hitX, hitY, hitZ);
				break;
			case 2:
				createSphere(wand, player, world, hand, facing, hitX, hitY, hitZ);
				break;
			}
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable NBTTagCompound tag)
	{
		return new BuildWandProvider();
	}
	
	public enum BuildWandShape
	{
		RECTANGULAR_PRISM, CYLINDER, SPHERE, DOME, RHOMBUS, TRIANGLE;
		
		public static final BuildWandShape[] BUILD_WAND_SHAPES = { RECTANGULAR_PRISM, CYLINDER, SPHERE, DOME, RHOMBUS, TRIANGLE };
		public static final String[] DISPLAY_NAMES = { "Rectangular Prism", "Cylinder", "Sphere", "Dome", "Rhombus", "Triangle" };
	}
	
	private void createRectPrism(ItemStack wand, EntityPlayer player, World world, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound tag = wand.getTagCompound();
		BlockPos pos1 = BlockPos.fromLong(tag.getLong("pos1"));
		BlockPos pos2 = BlockPos.fromLong(tag.getLong("pos2"));
		ItemStack stack = wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		ItemBlock itemBlock = null;
		Boolean setAir = false;
		if(stack.getItem() instanceof ItemAir)
			setAir = true;
		else
			itemBlock = (ItemBlock) stack.getItem();
		
		int minX = (pos1.getX() <= pos2.getX()) ? pos1.getX() : pos2.getX();
		int minY = (pos1.getY() <= pos2.getY()) ? pos1.getY() : pos2.getY();
		int minZ = (pos1.getZ() <= pos2.getZ()) ? pos1.getZ() : pos2.getZ();
		int maxX = (pos1.getX() > pos2.getX()) ? pos1.getX() : pos2.getX();
		int maxY = (pos1.getY() > pos2.getY()) ? pos1.getY() : pos2.getY();
		int maxZ = (pos1.getZ() > pos2.getZ()) ? pos1.getZ() : pos2.getZ();
		IBlockState fillBlock;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					BlockPos currentPos = new BlockPos(i, j, k);
					
					if(tag.getBoolean("isHollow"))
					{
						if(i == minX || i == maxX || j == minY || j == maxY || k == minZ || k == maxZ)
						{
							if(setAir)
							{
								world.setBlockToAir(currentPos);
							}
							else
							{
								fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
								world.setBlockState(currentPos, fillBlock);
							}
						}
					}
					else
					{
						if(setAir)
						{
							world.setBlockToAir(currentPos);
						}
						else
						{
							fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
							world.setBlockState(currentPos, fillBlock);
						}
					}
				}
			}
		}
		
		tag.removeTag("pos1");
		tag.removeTag("pos2");
		tag.removeTag("pos3");
		wand.setTagCompound(tag);
	}
	
	private void createCylinder(ItemStack wand, EntityPlayer player, World world, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound tag = wand.getTagCompound();
		BlockPos pos1 = BlockPos.fromLong(tag.getLong("pos1"));
		BlockPos pos2 = BlockPos.fromLong(tag.getLong("pos2"));
		BlockPos pos3 = BlockPos.fromLong(tag.getLong("pos3"));
		ItemStack stack = wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		ItemBlock itemBlock = null;
		Boolean setAir = false;
		if(stack.getItem() instanceof ItemAir)
			setAir = true;
		else
			itemBlock = (ItemBlock) stack.getItem();
		
		float radiusSq = (float) Math3d.pointToLineDistSq(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), pos3.getX(), pos3.getY(), pos3.getZ());
		float radius = (float) Math.sqrt(radiusSq);
		float height = (float) Math3d.getDistance(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		float heightSq = height * height;
		int minX = (pos1.getX() < pos2.getX() ? pos1.getX() : pos2.getX()) - (int) Math.floor(radius);
		int minY = (pos1.getY() < pos2.getY() ? pos1.getY() : pos2.getY()) - (int) Math.floor(radius);
		int minZ = (pos1.getZ() < pos2.getZ() ? pos1.getZ() : pos2.getZ()) - (int) Math.floor(radius);
		int maxX = (pos1.getX() > pos2.getX() ? pos1.getX() : pos2.getX()) + (int) Math.ceil(radius);
		int maxY = (pos1.getY() > pos2.getY() ? pos1.getY() : pos2.getY()) + (int) Math.ceil(radius);
		int maxZ = (pos1.getZ() > pos2.getZ() ? pos1.getZ() : pos2.getZ()) + (int) Math.ceil(radius);
		IBlockState fillBlock;
		
		for(int i = minX; i <= maxX; i++)
		{
			for(int j = minY; j <= maxY; j++)
			{
				for(int k = minZ; k <= maxZ; k++)
				{
					BlockPos currentPos = new BlockPos(i, j, k);
					
					float rSq = Math3d.pointInCylinder(pos1, pos2, heightSq, radiusSq, currentPos);
					if(rSq != -1.0F)
					{
						if(tag.getBoolean("isHollow"))
						{
							if(rSq >= (radius - 1) * (radius - 1))
							{
								if(setAir)
								{
									world.setBlockToAir(currentPos);
								}
								else
								{
									fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
									world.setBlockState(currentPos, fillBlock);
								}
							}
						}
						else
						{
							if(setAir)
							{
								world.setBlockToAir(currentPos);
							}
							else
							{
								fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
								world.setBlockState(currentPos, fillBlock);
							}
						}
					}
				}
			}
		}
		
		tag.removeTag("pos1");
		tag.removeTag("pos2");
		tag.removeTag("pos3");
		wand.setTagCompound(tag);
	}
	
	private void createSphere(ItemStack wand, EntityPlayer player, World world, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound tag = wand.getTagCompound();
		BlockPos pos1 = BlockPos.fromLong(tag.getLong("pos1"));
		BlockPos pos2 = BlockPos.fromLong(tag.getLong("pos2"));
		ItemStack stack = wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		ItemBlock itemBlock = null;
		Boolean setAir = false;
		if(stack.getItem() instanceof ItemAir)
			setAir = true;
		else
			itemBlock = (ItemBlock) stack.getItem();
		
		int radius = (int) Math.ceil((Math.sqrt(Math.pow(pos2.getX() - pos1.getX(), 2) + Math.pow(pos2.getY() - pos1.getY(), 2) + Math.pow(pos2.getZ() - pos1.getZ(), 2))));
		int minX = pos1.getX() - radius;
		int minY = pos1.getY() - radius;
		int minZ = pos1.getZ() - radius;
		int maxX = pos1.getX() + radius;
		int maxY = pos1.getY() + radius;
		int maxZ = pos1.getZ() + radius;
		IBlockState fillBlock;
		
		for(int i = minX; i <= maxX; ++i)
		{
			for(int j = minY; j <= maxY; ++j)
			{
				for(int k = minZ; k <= maxZ; ++k)
				{
					BlockPos currentPos = new BlockPos(i, j, k);
					
					if(Math.pow(i - pos1.getX(), 2) + Math.pow(j - pos1.getY(), 2) + Math.pow(k - pos1.getZ(), 2) <= radius * radius)
					{
						if(tag.getBoolean("isHollow"))
						{
							if(Math.pow(i - pos1.getX(), 2) + Math.pow(j - pos1.getY(), 2) + Math.pow(k - pos1.getZ(), 2) >= (radius - 1) * (radius - 1))
							{
								if(setAir)
								{
									world.setBlockToAir(currentPos);
								}
								else
								{
									fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
									world.setBlockState(currentPos, fillBlock);
								}
							}
						}
						else
						{
							if(setAir)
							{
								world.setBlockToAir(currentPos);
							}
							else
							{
								fillBlock = itemBlock.getBlock().getStateForPlacement(world, currentPos, facing, hitX, hitY, hitZ, itemBlock.getMetadata(stack), player, hand);
								world.setBlockState(currentPos, fillBlock);
							}
						}
					}
				}
			}
		}
		
		tag.removeTag("pos1");
		tag.removeTag("pos2");
		tag.removeTag("pos3");
		wand.setTagCompound(tag);
	}
}