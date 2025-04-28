package roidrole.patternbanners.recipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static roidrole.patternbanners.PatternBanners.pattern;

public class PatternFromShape
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe, IRecipeWrapper {

    String shape;
    ItemStack output;

    public PatternFromShape(ConfigCategory mapping){
        this.shape = mapping.get("shap").getString().substring(1, 10);
        this.output = new ItemStack(pattern, 1, mapping.get("meta").getInt());
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutput(VanillaTypes.ITEM, output);
        List<List<ItemStack>> inputs = new ArrayList<>(9);
        boolean addPaper = true;
        for(char chr : shape.toCharArray()){
            if(chr == '#'){
                inputs.add(OreDictionary.getOres("dye"));
            }else if (addPaper) {
                inputs.add(Collections.singletonList(Items.PAPER.getDefaultInstance()));
                addPaper = false;
            }else{
                inputs.add(Collections.singletonList(ItemStack.EMPTY));
            }
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, inputs);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
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
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }
}