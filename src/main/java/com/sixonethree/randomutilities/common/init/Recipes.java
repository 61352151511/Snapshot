package com.sixonethree.randomutilities.common.init;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

import com.sixonethree.randomutilities.api.ItemCombiningRecipeRegistry;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCreating;
import com.sixonethree.randomutilities.common.recipes.RecipesCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxDyeing;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxFeeding;
import com.sixonethree.randomutilities.common.recipes.RecipesUpgrading;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.Utilities;

public class Recipes {
	public static void init() {
		Category shapelessCategory = Category.SHAPELESS;
		String shapelessDependency = "after:minecraft:shapeless";
		
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combined_combining", RecipesCombinedCombining.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combined_creating", RecipesCombinedCreating.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combining", RecipesCombining.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "lunchbox_dyeing", RecipesLunchboxDyeing.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "lunchbox_feeding", RecipesLunchboxFeeding.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "upgrading", RecipesUpgrading.class, shapelessCategory, shapelessDependency);
		
		GameRegistry.addRecipe(new RecipesCombinedCombining());
		GameRegistry.addRecipe(new RecipesCombinedCreating());
		GameRegistry.addRecipe(new RecipesCombining());
		GameRegistry.addRecipe(new RecipesLunchboxDyeing());
		GameRegistry.addRecipe(new RecipesLunchboxFeeding());
		GameRegistry.addRecipe(new RecipesUpgrading());
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.heartCanister, 1, 0), "qdq", "igi", "qdq", 'q', new ItemStack(Blocks.quartz_block, 1, 0), 'd', new ItemStack(Items.diamond, 1, 0), 'i', new ItemStack(Items.iron_ingot), 'g', new ItemStack(Items.golden_apple, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.heartCanister, 1, 1), "gdg", "nen", "gdg", 'g', new ItemStack(Blocks.gold_block, 1, 0), 'd', new ItemStack(Blocks.diamond_block, 1, 0), 'n', new ItemStack(Items.nether_star), 'e', new ItemStack(Items.golden_apple, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.lunchbox, 1, 0), " s ", "iii", "cgb", 's', new ItemStack(Items.stick), 'i', new ItemStack(Items.iron_ingot), 'c', new ItemStack(Items.carrot), 'g', new ItemStack(Items.golden_apple), 'b', new ItemStack(Items.bread));
		
		ArrayList<ItemStack> UpgradeComponents = new ArrayList<ItemStack>() {
			private static final long serialVersionUID = -4349640254907860210L;
			{
				add(new ItemStack(Items.nether_star));
				add(new ItemStack(Items.bucket));
			}
		};
		
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 0), UpgradeComponents), new ItemStack(ModItems.heartCanister, 1, 2));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 1), UpgradeComponents), new ItemStack(ModItems.heartCanister, 1, 3));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), UpgradeComponents), new ItemStack(ModItems.lunchbox, 1, 1));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 3), new ItemStack(ModItems.lunchbox, 1, 1)), new ItemStack(ModItems.combined, 1, 0));
	}
	
	public static void initLunchboxRecipes() {
		for (Object obj : Item.itemRegistry) {
			if (obj instanceof Item) {
				Item item = (Item) obj;
				if (item instanceof ItemFood) {
					ItemFood food = (ItemFood) item;
					float foodPoints = food.getHealAmount(new ItemStack(item, 1, 0));
					ItemStack lunch = new ItemStack(ModItems.lunchbox, 1, 0);
					NBTTagCompound tag = new NBTTagCompound();
					tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, foodPoints);
					lunch.setTagCompound(tag);
					ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), new ItemStack(item, 1, 0)), lunch);
				}
			}
		}
		
		for (int i = 0; i < 16; i ++) {
			ItemStack lunchbox = new ItemStack(ModItems.lunchbox, 1, 0);
			lunchbox.setTagCompound(new NBTTagCompound());
			lunchbox.getTagCompound().setInteger(NBTTagKeys.COLOR, i);
			ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), new ItemStack(Items.dye, 1, i)), lunchbox);
		}
	}
}