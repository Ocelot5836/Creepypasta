package com.ocelot.crafting.workbench;

import java.util.List;

import com.google.common.collect.Lists;
import com.ocelot.inventory.InventoryCrafting;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CreepypastaWorkbenchShapelessRecipes implements ICreepypastaWorkbenchRecipe {

	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;
	/** Is a List of ItemStack that composes the recipe. */
	public final List<ItemStack> recipeItems;

	public CreepypastaWorkbenchShapelessRecipes(ItemStack output, List<ItemStack> inputList) {
		this.recipeOutput = output;
		this.recipeItems = inputList;
	}

	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return aitemstack;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> list = Lists.newArrayList(this.recipeItems);

		for (int x = 0; x < 8; ++x) {
			for (int y = 0; y < 8; ++y) {
				ItemStack itemstack = inv.getStackInRowAndColumn(x, y);

				if (!itemstack.isEmpty()) {
					boolean flag = false;

					for (ItemStack itemstack1 : list) {
						if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getMetadata() == 32767 || itemstack.getMetadata() == itemstack1.getMetadata())) {
							flag = true;
							list.remove(itemstack1);
							break;
						}
					}

					if (!flag) {
						return false;
					}
				}
			}
		}

		return list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return this.recipeItems.size();
	}
}