package roidrole.patternbanners.loom.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;

public class SlotDye extends Slot {
    public SlotDye(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.backgroundLocation = new ResourceLocation(Tags.MOD_ID, "textures/gui/container/slot/dye.png");
    }

    @Override
    public boolean isItemValid(ItemStack stack) {return Utils.isDye(stack);}
}
