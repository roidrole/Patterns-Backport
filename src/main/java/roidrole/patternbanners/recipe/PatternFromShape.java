package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.Utils;

import javax.annotation.Nonnull;

public class PatternFromShape extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public String shape;
    public ItemStack output;

    public PatternFromShape(String shape, ItemStack output){
        this.shape = shape;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean paper = false;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(shape.charAt(i) == ' ') {
                if(stack != ItemStack.EMPTY){return false;}
            }
            else{
                if (!Utils.isDye(stack)) {
                    return false;
                }
            }
            if(!paper && stack.getItem() == Items.PAPER){paper = true;}
        }
        return paper;
    }

    @Override
    public @Nonnull ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public @Nonnull ItemStack getRecipeOutput() {
        return output;
    }
}