package roidrole.patternbanners.recipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.recipe.wrapper.PatternApplyWrapper;
import roidrole.patternbanners.recipe.wrapper.RecipeAddPatternWrapper;

import java.util.Collections;

import static roidrole.patternbanners.recipe._Recipe.categoryUid;

@JEIPlugin
public class HEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry){

        registry.handleRecipes(PatternApply.class, PatternApplyWrapper::new, categoryUid);

        for(ConfigCategory mapping : Config.mappings){
            registry.addRecipes(Collections.singleton(new PatternApply(mapping)), categoryUid);
        }
        if(!Config.generalCategory.get("shapes_pattern").getBoolean()){
            for(BannerPattern pattern : BannerPattern.values()){
                if(pattern.hasPatternItem() || !pattern.hasPattern()){continue;}
                registry.addRecipes(Collections.singletonList(new RecipeAddPatternWrapper(pattern)), categoryUid);
            }
        }

        registry.addRecipeCatalyst(new ItemStack(Items.BANNER, 1, OreDictionary.WILDCARD_VALUE), categoryUid);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        registry.addRecipeCategories(new RecipeCategoryHEI(registry.getJeiHelpers().getGuiHelper()));
    }
}