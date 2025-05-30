package roidrole.patternbanners.recipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.recipe.wrapper.PatternApplyWrapper;
import roidrole.patternbanners.recipe.wrapper.PatternFromShapeWrapper;
import roidrole.patternbanners.recipe.wrapper.PatternOnlyShapeWrapper;

import static roidrole.patternbanners.loom._Loom.loomBlock;
import static roidrole.patternbanners.recipe._Recipe.PATTERNS_ONLY_SHAPE;
import static roidrole.patternbanners.recipe._Recipe.PATTERN_APPLY_RECIPES;

@JEIPlugin
public class HEIPlugin implements IModPlugin {
    public static final String categoryUid = Tags.MOD_ID+"pattern";
    @Override
    public void register(IModRegistry registry){

        registry.handleRecipes(PatternApply.class, PatternApplyWrapper::new, categoryUid);
        registry.addRecipes(PATTERN_APPLY_RECIPES, categoryUid);

        if(ConfigGeneral.shapes_pattern){
            registry.handleRecipes(PatternFromShape.class, PatternFromShapeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);
        } else {
            registry.handleRecipes(BannerPattern.class, PatternOnlyShapeWrapper::new, categoryUid);
            registry.addRecipes(PATTERNS_ONLY_SHAPE, categoryUid);
        }


        registry.addRecipeCatalyst(new ItemStack(Items.BANNER, 1, OreDictionary.WILDCARD_VALUE), categoryUid);
        if(ConfigGeneral.Recipes.craftingTable) {
            registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE, 1, 0), categoryUid);
        }
        if(ConfigGeneral.Recipes.loom){
            registry.addRecipeCatalyst(new ItemStack(loomBlock, 1, 0), categoryUid);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        registry.addRecipeCategories(new RecipeCategoryHEI(registry.getJeiHelpers().getGuiHelper()));
    }
}