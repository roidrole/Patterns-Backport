package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;

import javax.annotation.Nonnull;

public class PatternOnlyShape extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public String shape;
    public String hash;

    public PatternOnlyShape(BannerPattern pattern){

        this.shape = String.join("", pattern.getPatterns());
        this.hash = pattern.getHashname();
        this.setRegistryName(Tags.MOD_ID, "recipes/pattern_apply/" + hash);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean banner = false;
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
            if(!banner && stack.getItem() == Items.BANNER){banner = true;}
        }
        return banner;
    }

    @Override
    public @Nonnull ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack output = ItemStack.EMPTY;
        for (int i = 0; i < 9; i++) {
            output = inv.getStackInSlot(i);
            if(output.getItem() == Items.BANNER){break;}
        }
        output = output.copy();
        output.setCount(1);
        Utils.addPattern(output, Utils.getDyeColor(inv.getStackInSlot(2)), hash);
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public ItemStack getRecipeOutput() {return ItemStack.EMPTY;}
}