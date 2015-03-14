package com.sixonethree.randomutilities.common.block;

import com.sixonethree.randomutilities.common.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMagicChest extends Container {
	private EntityPlayer player;
	private IInventory chest;
	
	public ContainerMagicChest(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		chest = chestInventory;
		player = ((InventoryPlayer) playerInventory).player;
		chestInventory.openInventory(player);
		layoutContainer(playerInventory, chestInventory, xSize, ySize);
	}
	
	@Override public boolean canInteractWith(EntityPlayer player) {
		return chest.isUseableByPlayer(player);
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 1) {
				if (!mergeItemStack(itemstack1, 1, inventorySlots.size(), true)) { return null; }
			} else if (!slot.isItemValid(itemstack1)) {
				return null;
			} else if (!mergeItemStack(itemstack1, 0, 1, false)) { return null; }
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		chest.markDirty();
		return itemstack;
	}
	
	@Override public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		chest.closeInventory(entityplayer);
	}
	
	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		addSlotToContainer(new ValidatingSlot(chestInventory, 0, 80, 18, false));
		addSlotToContainer(new ValidatingSlot(chestInventory, 1, 152, 18, true, new ItemStack(ModItems.magicCard)));
		
		int leftCol = (xSize - 162) / 2 + 1;
		for (int playerInvRow = 0; playerInvRow < 3; playerInvRow ++) {
			for (int playerInvCol = 0; playerInvCol < 9; playerInvCol ++) {
				addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
			}
			
		}
		
		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot ++) {
			addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
		}
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
}