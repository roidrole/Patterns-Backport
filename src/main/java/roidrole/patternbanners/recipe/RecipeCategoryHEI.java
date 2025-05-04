package roidrole.patternbanners.recipe;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.item.ItemModelMapper;

import javax.annotation.Nullable;

import static roidrole.patternbanners.recipe._Recipe.categoryUid;

public class RecipeCategoryHEI implements IRecipeCategory<IRecipeWrapper> {
    IGuiHelper guiHelper;

    public RecipeCategoryHEI(IGuiHelper guiHelper) {this.guiHelper = guiHelper;}

    @Override
    public String getUid() {return categoryUid;}

    @Override
    public String getTitle() {return I18n.format("patternbanners.category.patternapply");}

    @Override
    public String getModName() {return PatternBanners.NAME;}

    @Override
    public IDrawable getBackground() {return guiHelper.createBlankDrawable(140, 100);}

    @Nullable
    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawable(ItemModelMapper.defaultPatternModel, 0, 0, 16, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingr) {
        if(!(wrapper instanceof PatternApplyWrapper)) return;

        layout.getItemStacks().init(0, true, 4, 4);
        layout.getItemStacks().set(0, ingr.getInputs(VanillaTypes.ITEM).get(0));
    }
}
