package roidrole.patternbanners.recipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PatternApplyWrapper implements IRecipeWrapper {
    ItemStack patternI;
    String patternN;

    public PatternApplyWrapper(PatternApply recipe){
        patternI = recipe.patternI;
        patternN = recipe.patternN;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, patternI);
    }

    @Override
    public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/entity/banner/"+patternN+".png"));
        drawTexturedModalRect(50, 0, 0, 2, 2, 39, 78, 0.008f, 0.008f);
    }

    //Helpers
    //Took from Just Enough Pattern Banners, licensed LGPL. Repo link : https://github.com/Lorexe/JustEnoughPatternBanners
    public void drawTexturedModalRect(int x, int y, float z, int u, int v, int w, int h, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + h, z).tex(u * f, (v + h) * f1).endVertex();
        tessellator.getBuffer().pos(x + w, y + h, z).tex((u + w) * f, (v + h) * f1).endVertex();
        tessellator.getBuffer().pos(x + w, y, z).tex((u + w) * f, v * f1).endVertex();
        tessellator.getBuffer().pos(x, y, z).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }
}
