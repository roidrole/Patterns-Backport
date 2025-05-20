package roidrole.patternbanners.loom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static roidrole.patternbanners.recipe._Recipe.PATTERNS_ONLY_SHAPE;

@SideOnly(Side.CLIENT)
public class GuiLoom extends GuiContainer {
    private static final int SLOT_SIZE = 14;
    public static List<ResourceLocation> patternLocs = new ArrayList<>();
    private int firstRenderedLine = 0;
    private static boolean hasScroll;
    int scrollAmount;
    int scrollPixels = 41;
    public GuiLoom(InventoryPlayer inventory, World world, BlockPos pos) {
        super(new ContainerLoom(inventory, world, pos));
        this.height=176;
        this.width=166;

        for (BannerPattern pattern : PATTERNS_ONLY_SHAPE){
            patternLocs.add(new ResourceLocation("minecraft", "textures/entity/banner/"+pattern.getFileName()+".png"));
        }
        hasScroll = patternLocs.size() > 16 || true;
        if(hasScroll) {
            scrollAmount = patternLocs.size() / 4 - 4;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(hasScroll){
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
            drawTexturedModalRect(
                    guiLeft + 12 - 6, guiTop + SLOT_SIZE * firstRenderedLine,
                    232, 242, // Thumb texture position
                    6, 15
            );
        }
        //Render the items
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.loom"), 8, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/container/loom.png"));
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    //TODO : Finish implementing banner preview. FutureMC adds a listener to craftingMatrix. Vanilla output preview? Custom slot galore?
    //Just send an int representing the PATTERN_ONLY_RECIPES index of the pattern and calc on the server
    //Should check if there are enough PATTERN_ONLY_RECIPES to justify the bar. Vanilla is
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    //TODO : implement slots. Draw all slots at the same time to minimize isItemInSlot
}