package com.sixonethree.randomutilities.common.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.client.ValidatingSlot;

public class ContainerDisplayTable extends Container {
	private EntityPlayer player;
	private IInventory inventory;
	
	/* Constructors */
	
	public ContainerDisplayTable(IInventory playerInv, IInventory inventory, int xSize, int ySize) {
		this.player = ((InventoryPlayer) playerInv).player;
		this.inventory = inventory;
		inventory.openInventory(player);
		layoutContainer(playerInv, inventory, xSize, ySize);
	}
	
	/* Custom Methods */
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		int totalSlotsAdded = 0;
		for (int y = 1; y <= 5; y ++) {
			for (int x = 1; x <= 5; x ++) {
				this.addSlotToContainer(new ValidatingSlot(chestInventory, totalSlotsAdded, 44 + (x * 18), (y * 18) - 2, false));
				totalSlotsAdded ++;
			}
		}
		
		int leftCol = (xSize - 162) / 2 + 1;
		for (int playerInvRow = 0; playerInvRow < 3; playerInvRow ++) {
			for (int playerInvCol = 0; playerInvCol < 9; playerInvCol ++) {
				this.addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
			}
			
		}
		
		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot ++) {
			this.addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
		}
	}
	
	/* Overridden */
	
	@Override public boolean canInteractWith(EntityPlayer playerIn) {
		return this.inventory.isUseableByPlayer(playerIn);
	}
	
	@Override public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.inventory.closeInventory(playerIn);
	}
	
	@Override @Nullable public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < this.inventory.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) { return null; }
			} else if (!mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) { return null; }
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
}