package roidrole.patternbanners.loom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.loom.slot.SlotBanner;
import roidrole.patternbanners.loom.slot.SlotBannerOutput;
import roidrole.patternbanners.loom.slot.SlotDye;
import roidrole.patternbanners.loom.slot.SlotPattern;
import roidrole.patternbanners.recipe.PatternApply;

import static roidrole.patternbanners.loom._Loom.loomBlock;
import static roidrole.patternbanners.recipe._Recipe.LOOM_RECIPES;

public class ContainerLoom extends Container {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 1);
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    private final BlockPos pos;
    public World world;
    private final EntityPlayer player;

    public ContainerLoom(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.player = playerInventory.player;
        this.addSlotToContainer(new SlotBanner(this.craftMatrix, 0, 13, 26));
        this.addSlotToContainer(new SlotPattern(this.craftMatrix, 1, 23, 45));
        this.addSlotToContainer(new SlotDye(this.craftMatrix, 2, 33, 26));
        this.addSlotToContainer(new SlotBannerOutput(player, this.craftMatrix,  this.craftResult, this, 0, 143, 57));

        //Player Inventory
        for (int k = 0; k < 3; ++k) {
            for (int i = 0; i < 9; ++i)
            {
                this.addSlotToContainer(new Slot(playerInventory, i + k * 9 + 9, 8 + i * 18, 84 + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }


    public void onCraftMatrixChanged(IInventory inventoryIn) {
        //Can't use annotations here. I guess it must return *something* on client
        if(world.isRemote){return;}
        EntityPlayerMP player = (EntityPlayerMP) this.player;
        ItemStack output = ItemStack.EMPTY;
        for(PatternApply recipe : LOOM_RECIPES){
            if (recipe.patternI.isItemEqual(inventoryIn.getStackInSlot(1)) && recipe.matches(craftMatrix, world)) {
                this.craftResult.setRecipeUsed(recipe);
                output = recipe.getCraftingResult((InventoryCrafting) inventoryIn);
                break;
            }
        }
        if(!output.equals(this.craftResult.getStackInSlot(0))){
            this.craftResult.setInventorySlotContents(0, output);
            player.connection.sendPacket(new SPacketSetSlot(this.windowId, 3, output));
        }
    }

    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if(!this.world.isRemote){
            this.clearContainer(playerIn, this.world, this.craftMatrix);
        }
    }


    public boolean canInteractWith(EntityPlayer playerIn) {
        if (this.world.getBlockState(this.pos).getBlock() != loomBlock) {
            return false;
        } else {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 3) { //Output slot
                if (!mergeItemStack(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 3) { //Inventory slot
                if (itemstack1.getItem() instanceof ItemBanner) {
                    if (!mergeItemStack(itemstack1, 0, 1, false)) { //Banner slot
                        return ItemStack.EMPTY;
                    }
                } else if (Utils.isPatternItem(itemstack1)) {
                    if (!mergeItemStack(itemstack1, 1, 2, false)) { //Pattern slot
                        return ItemStack.EMPTY;
                    }
                }else if (Utils.isDye(itemstack1)) {
                    if (!mergeItemStack(itemstack1, 2, 3, false)) { //Dye slot
                        return ItemStack.EMPTY;
                    }
                }  else if (index <= 30) {
                    if (!mergeItemStack(itemstack1, 31, 40, false)) { //Regular inventory
                        return ItemStack.EMPTY;
                    }
                } else if (index <= 39 && !mergeItemStack(itemstack1, 4, 31, false)) { //Hotbar
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, 4, 40, false)) { //Crafting Matrix
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) { //Empty slot after everythind
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}