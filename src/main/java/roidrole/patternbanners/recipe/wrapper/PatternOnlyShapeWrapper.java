package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.config.ConfigGeneral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternOnlyShapeWrapper extends PatternRecipeWrapper {
    String patternS;

    public PatternOnlyShapeWrapper(BannerPattern patternIn){
        patternN = patternIn.getFileName();
        patternS = String.join("", patternIn.getPatterns());
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        boolean flagBanner = true;
        List<List<ItemStack>> ingredientsTemp = new ArrayList<>(9);
        for (char c : patternS.toCharArray()){
            if(c == '#'){
                ingredientsTemp.add(OreDictionary.getOres("dyeWhite"));
            }else if(flagBanner){
                ingredientsTemp.add(Collections.singletonList(new ItemStack(Items.BANNER, 1, 0)));
                flagBanner = false;
            }else{
                ingredientsTemp.add(Collections.singletonList(ItemStack.EMPTY));
            }
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, ingredientsTemp);
    }

    @Override
    public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(mc, recipeWidth, recipeHeight, mouseX, mouseY);
        if(!ConfigGeneral.recipes.JEICraftingTable) {
            mc.getTextureManager().bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/container/slot/banner_pattern.png"));
            drawTexturedModalRect(17, 31, 0, 0, 16, 16, 0.0625f, 0.0625f);
        }
    }
}
