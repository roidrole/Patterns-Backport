package roidrole.patternbanners.loom.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import roidrole.patternbanners.Utils;

public class SlotDye extends Slot {
    public SlotDye(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    @Override
    public boolean isItemValid(ItemStack stack) {return Utils.isDye(stack);}
}
