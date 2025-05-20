package roidrole.patternbanners.recipe;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.recipe.wrapper.PatternRecipeWrapper;

import javax.annotation.Nullable;

import static roidrole.patternbanners.recipe.HEIPlugin.categoryUid;

public class RecipeCategoryHEI implements IRecipeCategory<IRecipeWrapper> {
    //TODO : draw differently if no CraftingTableRecipe
    IGuiHelper guiHelper;

    public RecipeCategoryHEI(IGuiHelper guiHelper) {this.guiHelper = guiHelper;}

    @Override
    public String getUid() {return categoryUid;}

    @Override
    public String getTitle() {return I18n.format("patternbanners.category.patternapply");}

    @Override
    public String getModName() {return Tags.MOD_NAME;}

    @Override
    public IDrawable getBackground() {return guiHelper.createBlankDrawable(140, 100);}

    @Override
    public void drawExtras(Minecraft mc) {
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; ++x) {
                guiHelper.getSlotDrawable().draw(mc, 4 + x * 18, 4 + y * 18);
            }
        }

    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return guiHelper.drawableBuilder(
                new ResourceLocation("patternbanners:textures/items/pattern/creeper.png"),
                0, 0, 16, 16
        ).setTextureSize(16, 16).build();
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingr) {
        if(!(wrapper instanceof PatternRecipeWrapper)) return;

        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; ++x) {
                int index = x + (y * 3);
                guiItemStacks.init(index, true, 4 + x * 18, 4 + y * 18);
            }
        }
        for (int i = 0; i < ingr.getInputs(VanillaTypes.ITEM).size(); i++) {
            guiItemStacks.set(i, ingr.getInputs(VanillaTypes.ITEM).get(i));
        }
    }
}
