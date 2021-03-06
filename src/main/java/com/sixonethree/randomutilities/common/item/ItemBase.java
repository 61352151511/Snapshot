package com.sixonethree.randomutilities.common.item;

import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemBase extends Item {
	
	/* Constructors */
	
	public ItemBase() {
		super();
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	
	/* Custom Methods */
	
	public ItemBase setNames(String name) {
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		this.setUnlocalizedName(this.getRegistryName().toString());
		return this;
	}
	
	public void tagCompoundVerification(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
	}
	
	public int tagOrDefault(ItemStack stack, String key, int def) {
		this.tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(key) ? stack.getTagCompound().getInteger(key) : def;
	}
	
	public float tagOrDefault(ItemStack stack, String key, float def) {
		this.tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(key) ? stack.getTagCompound().getFloat(key) : def;
	}
	
	public String tagOrDefault(ItemStack stack, String key, String def) {
		this.tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(key) ? stack.getTagCompound().getString(key) : def;
	}
}