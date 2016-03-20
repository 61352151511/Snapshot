package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemLunchbox extends ItemBase implements ILunchbox {
	String[] nameSuffixes = new String[] {"", "_auto"};
	public IItemColor lunchbox = new IItemColor() {
		@Override public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			return tintIndex == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(getColor(stack));
		}
	};
	
	public ItemLunchbox() {
		super();
		this.setUnlocalizedName("lunchbox");
		this.setFull3D();
		this.setHasSubtypes(true);
	}
	
	public IItemColor getItemColor() {
		return this.lunchbox;
	}
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
		list.add(TextFormatting.AQUA + I18n.translateToLocal("tooltip.lunchbox.stores"));
		float storedFood = this.getCurrentFoodStorage(stack);
		float maximum = this.getMaxFoodStorage(stack);
		if (this.isLunchboxAutomatic(stack)) list.add(TextFormatting.AQUA + I18n.translateToLocal("tooltip.lunchbox.auto"));
		list.add(TextFormatting.GREEN + I18n.translateToLocal("tooltip.lunchbox.fill"));
		String storedAsString = String.valueOf(storedFood / 2);
		String maximumStoredAsString = String.valueOf(maximum / 2);
		if (storedAsString.contains(".")) storedAsString = storedAsString.substring(0, storedAsString.indexOf(".") + 2);
		if (storedAsString.endsWith(".0")) storedAsString = storedAsString.replace(".0", "");
		if (maximumStoredAsString.endsWith(".0")) maximumStoredAsString = maximumStoredAsString.replace(".0", "");
		list.add(Utilities.translateFormatted("tooltip.lunchbox.stored", storedAsString, maximumStoredAsString));
	}
	
	@Override public EnumAction getItemUseAction(ItemStack stack) { return this.isLunchboxAutomatic(stack) ? EnumAction.NONE : EnumAction.EAT; }
	
	@Override @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + this.nameSuffixes[stack.getItemDamage()]; }
	@Override public int getMaxItemUseDuration(ItemStack stack) { return 32; }
	@Override public boolean hasEffect(ItemStack stack) { return stack.getItemDamage() == 1; }
	
	@Override public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.canEat(false)) {
			playerIn.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
	}
	
	@Override public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!(entityLiving instanceof EntityPlayer)) return stack;
		EntityPlayer player = (EntityPlayer) entityLiving;
		int playerFood = player.getFoodStats().getFoodLevel();
		if (playerFood < 20) {
			int storedFood = (int) this.getCurrentFoodStorage(stack);
			int foodToGive = 20 - playerFood;
			if (foodToGive > storedFood) {
				foodToGive = storedFood;
			}
			player.getFoodStats().addStats(foodToGive, foodToGive > 0 ? 20F : 0F);
			stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storedFood - foodToGive);
		}
		return stack;
	}
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (stack.getItemDamage() == 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int playerFood = player.getFoodStats().getFoodLevel();
			if (playerFood < 20) {
				int storedFood = (int) this.getCurrentFoodStorage(stack);
				int foodToGive = 20 - playerFood;
				if (foodToGive > storedFood) {
					foodToGive = storedFood;
				}
				player.getFoodStats().addStats(foodToGive, foodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storedFood - foodToGive);
			}
		}
	}
	
	/* ILunchbox */
	
	@Override public boolean isLunchboxAutomatic(ItemStack stack) {
		return stack.getMetadata() == 1;
	}
	
	@Override public void setCurrentFoodStorage(ItemStack stack, float storage) {
		this.tagCompoundVerification(stack);
		stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storage);
	}
}