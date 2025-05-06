package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.recipe.PatternApply;

import java.util.Arrays;
import java.util.Collections;

public class PatternApplyWrapper extends PatternRecipeWrapper {
    ItemStack patternI;

    public PatternApplyWrapper(PatternApply recipe){
        patternI = recipe.patternI;
        patternN = recipe.patternN;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(
                Collections.singletonList(new ItemStack(Items.BANNER, 1, 0)),
                Collections.singletonList(patternI),
                OreDictionary.getOres("dyeWhite")
        ));
    }
}
