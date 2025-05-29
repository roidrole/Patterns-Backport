package roidrole.patternbanners.loom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
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
    pattern -> new ResourceLocation("minecraft:textures/entity/banner/"+pattern.getFileName()+".png")).collect(Collectors.toList());
    public static final boolean hasScroll = patternLocs.size() > 16;

    static int maxRenderedLine;

    private int firstRenderedLine = 0;
    private int slotSelected = -1;
    private final ContainerLoom container;
    public GuiLoom(ContainerLoom container) {
        super(container);
        this.container = container;
        if(hasScroll){
            maxRenderedLine = Math.floorDiv(patternLocs.size(), 4) - 4;
        }
    }
    //TODO : BTW, the first three lines are unconditionally rendered if hasScroll
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.loom"), 8, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //Background
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/container/loom.png"));
        int x = this.guiLeft;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        //Thumb
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
        this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+13+getThumbOffset(firstRenderedLine), 232 +(hasScroll?0:12 ), 0, 12, 15);

        //Other slots
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/slot_full.png"));
        int lastFullItemSlot = Math.min(15, patternLocs.size() -(firstRenderedLine *4));
        for (int index = 0; index <= lastFullItemSlot; index++) {
            x = this.guiLeft+60 + (index % 4) * 14;
            y = this.guiTop+13 + Math.floorDiv(index, 4)*14;
            if (index != slotSelected) {
                drawModalRectWithCustomSizedTexture(x, y, 0, 0, 14, 14, 14, 14);
            }
        }

        //Selected Slot
        if(slotSelected >=0 && slotSelected < 16){
            x = this.guiLeft+60 + (slotSelected % 4) * 14;
            y = this.guiTop+13 + Math.floorDiv(slotSelected, 4)*14;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/slot_selected.png"));
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, 14, 14, 14, 14);
        }

        //Banners
        for (int index = 0; index <= lastFullItemSlot; index++) {
            x = this.guiLeft+60+4 + (index % 4) * 14;
            y = this.guiTop+13+2 + Math.floorDiv(index, 4)*14;
            Minecraft.getMinecraft().getTextureManager().bindTexture(patternLocs.get(firstRenderedLine * 4 + index));
            drawScaledCustomSizeModalRect(x, y, 1, 1, 20, 40, 5, 10, 64, 64);
        }

        //Preview
        if(container.getSlot(3).getHasStack()){
            drawBannerPreview(container.getSlot(3).getStack(), guiLeft+141, guiTop+8, 20, 40);
        }
    }
    //TODO:Add click and drag thumb

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseButton != 0 && mouseButton != 1){return;}
        int lineClicked = Math.floorDiv(mouseY-(this.guiTop+13), 14);
        if(lineClicked < 0 || lineClicked > 3){return;}
        int columnClicked = Math.floorDiv(mouseX-(this.guiLeft+60), 14);
        if(columnClicked < 0 || columnClicked > 3){return;}
        slotSelected = 4*lineClicked + columnClicked;
        int recipeSelected = 4 * (lineClicked + firstRenderedLine) + columnClicked;
        Minecraft.getMinecraft().player.connection.sendPacket(
                new CPacketEnchantItem(container.windowId, recipeSelected)
        );
    }

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
            slotSelected += 4;
        } else {
            if(this.firstRenderedLine >= maxRenderedLine){return;}
            firstRenderedLine++;
            slotSelected -= 4;
        }
    }

    //Helpers
    public static int getThumbOffset(int first){
        if(first == maxRenderedLine){return 41;}
        return first*Math.floorDiv(41, maxRenderedLine);
    }

    public void drawBannerPreview(ItemStack bannerStack, int x, int y, int w, int h) {
        TileEntityBanner bannerTile = new TileEntityBanner();
        bannerTile.setItemValues(bannerStack, false);
        for (int i = 0; i < TileEntityBanner.getPatterns(bannerStack); i++) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(
                    BannerTextures.BANNER_DESIGNS.getResourceLocation(
                            bannerTile.getPatternResourceLocation(),
                            bannerTile.getPatternList(),
                            bannerTile.getColorList()
                    )
            );
            drawModalRectWithCustomSizedTexture(x, y, 1, 1, w, h, 64, 64);
            GlStateManager.color(1, 1, 1); // Reset color
        }
    }
}