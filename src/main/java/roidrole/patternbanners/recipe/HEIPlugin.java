package roidrole.patternbanners.recipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.config.Config;

import java.util.Collections;

import static roidrole.patternbanners.recipe._Recipe.categoryUid;

@JEIPlugin
public class HEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry){
        for(ConfigCategory mapping : Config.mappings){
            registry.addRecipes(Collections.singleton(new PatternApply(mapping)), categoryUid);
            registry.addRecipeCatalyst(new ItemStack(PatternBanners.pattern, 1, mapping.get("meta").getInt()), categoryUid);
        }
        registry.handleRecipes(PatternApply.class, PatternApplyWrapper::new, categoryUid);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        registry.addRecipeCategories(new RecipeCategoryHEI(registry.getJeiHelpers().getGuiHelper()));
    }
}