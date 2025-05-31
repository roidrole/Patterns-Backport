package roidrole.patternbanners.recipe.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class PatternRecipeWrapper implements IRecipeWrapper {
    String patternN;

    @Override
    public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        drawRect(82, 0, 121, 78, 0, 0, 0, 255);
        mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/entity/banner/"+patternN+".png"));
        drawTexturedModalRect(82, 0, 2, 2, 39, 78, 0.008f, 0.008f);
    }

    public static void drawRect(int left, int top, int right, int bottom, int R, int G, int B, int A) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(R, G, B, A);
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.color(255, 255, 255, 255);
    }

    public static void drawTexturedModalRect(int x, int y, int u, int v, int w, int h, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + h, 0.0D).tex(u * f, (v + h) * f1).endVertex();
        bufferbuilder.pos(x + w, y + h, 0.0D).tex((u + w) * f, (v + h) * f1).endVertex();
        bufferbuilder.pos(x + w, y, 0.0D).tex((u + w) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }
}
