package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;

import java.util.ArrayList;
import java.util.List;

public class RecipeAddPatternWrapper extends PatternRecipeWrapper {
    String patternS;

    public RecipeAddPatternWrapper(BannerPattern patternIn){
        patternN = patternIn.getFileName();
        patternS = String.join("", patternIn.getPatterns());
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        boolean flagBanner = true;
        List<ItemStack> ingredientsTemp = new ArrayList<>(9);
        for (char c : patternS.toCharArray()){
            if(c == '#'){
                ingredientsTemp.add(new ItemStack(Items.DYE, 1, 15));
            }else if(flagBanner){
                ingredientsTemp.add(new ItemStack(Items.BANNER, 1, 0));
                flagBanner = false;
            }else{
                ingredientsTemp.add(ItemStack.EMPTY);
            }
        }
        iIngredients.setInputs(VanillaTypes.ITEM, ingredientsTemp);
    }
}
