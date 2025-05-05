package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import roidrole.patternbanners.recipe.PatternApply;

import java.util.Arrays;

public class PatternApplyWrapper extends PatternRecipeWrapper {
    ItemStack patternI;

    public PatternApplyWrapper(PatternApply recipe){
        patternI = recipe.patternI;
        patternN = recipe.patternN;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputs(VanillaTypes.ITEM, Arrays.asList(
                new ItemStack(Items.BANNER, 1, 0),
                patternI,
                new ItemStack(Items.DYE, 1, 15)
        ));
    }
}
