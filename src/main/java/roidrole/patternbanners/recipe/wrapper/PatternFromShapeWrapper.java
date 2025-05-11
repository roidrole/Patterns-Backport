package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.recipe.PatternFromShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternFromShapeWrapper implements IRecipeWrapper {
    ItemStack output;
    String shape;

    public PatternFromShapeWrapper(PatternFromShape recipe){
        this.output = recipe.output;
        this.shape = recipe.shape;
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
}
