package com.sythiex.sythiexstuff.inventory;

import com.sythiex.sythiexstuff.init.SythiexStuffItems;
import com.sythiex.sythiexstuff.item.ItemBuildWand;
import com.sythiex.sythiexstuff.proxy.CommonProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContainerBuildWand extends Container
{
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int WAND_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
	private InventoryPlayer inventoryPlayer;
	
	public ContainerBuildWand(InventoryPlayer inventoryPlayer, IItemHandler wandItemHandler)
	{
		this.inventoryPlayer = inventoryPlayer;
		
		final int Y_OFFSET = 69;
		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = Y_OFFSET + 82;
		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = Y_OFFSET + 24;
		
		// player hotbar 0 - 8
		for(int slotNumber = 0; slotNumber < HOTBAR_SLOT_COUNT; slotNumber++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * slotNumber, HOTBAR_YPOS));
		}
		
		// player inventory 9 - 35
		for(int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++)
		{
			for(int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++)
			{
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(inventoryPlayer, slotNumber, xpos, ypos));
			}
		}
		
		// wand slot 36
		addSlotToContainer(new SlotItemHandlerBlocksOnly(wandItemHandler, 0, 45, Y_OFFSET));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.getHeldItemMainhand().getItem() instanceof ItemBuildWand;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		// if the slot clicked is the wand that is in use, cancel the click
		if(slotId >= 0 && slotId < HOTBAR_SLOT_COUNT)
		{
			if(inventoryPlayer.getStackInSlot(slotId).getItem() == SythiexStuffItems.itemBuildWand)
				return ItemStack.EMPTY;
		}
		
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot) inventorySlots.get(sourceSlotIndex);
		if(sourceSlot == null || !sourceSlot.getHasStack())
			return ItemStack.EMPTY;
		
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		if(sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT)
		{
			// merge stack to wand slot
			if(!mergeItemStack(sourceStack, WAND_SLOT_INDEX, WAND_SLOT_INDEX + 1, false))
				return ItemStack.EMPTY;
		}
		else if(sourceSlotIndex == WAND_SLOT_INDEX)
		{
			// merge stack to player inventory
			if(!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
				return ItemStack.EMPTY;
		}
		else
		{
			CommonProxy.logger.error("Invalid slot index moving stack to/from Creative Build Wand. Index: " + sourceSlotIndex);
			return ItemStack.EMPTY;
		}
		
		// if entire stack was moved, set to empty
		if(sourceStack.getCount() == 0)
		{
			sourceSlot.putStack(ItemStack.EMPTY);
		}
		else
		{
			sourceSlot.onSlotChanged();
		}
		
		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}
}