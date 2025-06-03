package roidrole.patternbanners.loom.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;

public class SlotPattern extends Slot {
    public SlotPattern(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.backgroundLocation = new ResourceLocation(Tags.MOD_ID, "textures/gui/container/slot/banner_pattern.png");
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return Utils.isPatternItem(stack);
    }
}

