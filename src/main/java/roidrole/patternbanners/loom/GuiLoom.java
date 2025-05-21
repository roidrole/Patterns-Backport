package roidrole.patternbanners.loom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import roidrole.patternbanners.Tags;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static roidrole.patternbanners.recipe._Recipe.PATTERNS_ONLY_SHAPE;

@SideOnly(Side.CLIENT)
public class GuiLoom extends GuiContainer {
    public static final List<ResourceLocation> patternLocs = PATTERNS_ONLY_SHAPE.stream().map(
    pattern -> new ResourceLocation("textures/entity/banner/"+pattern.getFileName()+".png")).collect(Collectors.toList());
    public static final boolean hasScroll = patternLocs.size() > 16;

    static int maxRenderedLine;

    private int firstRenderedLine = 0;
    public GuiLoom(InventoryPlayer inventory, World world, BlockPos pos) {
        super(new ContainerLoom(inventory, world, pos));
        if(hasScroll){
            maxRenderedLine = Math.floorDiv(patternLocs.size(), 4) - 4;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

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

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
        this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+13+getThumbOffset(firstRenderedLine), 232 +(hasScroll?0:12 ), 0, 12, 15);
    }

    //TODO : Finish implementing banner preview. FutureMC adds a listener to craftingMatrix. Vanilla output preview? Custom slot galore?
    //Just send an int representing the PATTERN_ONLY_RECIPES index of the pattern and calc on the server
    //Should check if there are enough PATTERN_ONLY_RECIPES to justify the bar. Vanilla is
    //TODO:Add click and drag slot

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (Mouse.getEventDWheel() == 0) {return;}
        if(Mouse.getEventDWheel() > 0){
            if(this.firstRenderedLine <= 0){return;}
            firstRenderedLine--;
        } else {
            if(this.firstRenderedLine >= maxRenderedLine){return;}
            firstRenderedLine++;
        }
    }

    //TODO : implement slots. Draw all slots at the same time to minimize isItemInSlot
    //Helpers
    public static int getThumbOffset(int first){
        if(first == maxRenderedLine){return 41;}
        return first*Math.floorDiv(41, maxRenderedLine);
    }
}