package roidrole.patternbanners.loom.slot;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;
import roidrole.patternbanners.recipe.PatternApply;

public class SlotBannerOutput extends SlotCrafting {
    InventoryCrafting craftMatrix;
    InventoryCraftResult craftResult;
    EntityPlayer player;
    Container container;
    public SlotBannerOutput(EntityPlayer player, InventoryCrafting craftingInventory, InventoryCraftResult inventoryIn, Container containerIn, int slotIndex, int xPosition, int yPosition) {
        super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
        this.craftMatrix = craftingInventory;
        this.player = player;
        this.craftResult = inventoryIn;
        this.container = containerIn;
    }

    @Override
    protected void onCrafting(ItemStack stack) {
        super.onCrafting(stack);
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        if(thePlayer instanceof EntityPlayerSP){return ItemStack.EMPTY;}
        this.onCrafting(stack);

        NonNullList<ItemStack> nonnulllist;
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(thePlayer);
        if(this.craftResult.getRecipeUsed() instanceof PatternApply){
            nonnulllist = this.craftResult.getRecipeUsed().getRemainingItems(craftMatrix);
        }else{
            nonnulllist = NonNullList.withSize(3, ItemStack.EMPTY);
        }
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack input = this.craftMatrix.getStackInSlot(i);
            ItemStack remaining = nonnulllist.get(i);

            if (input.isItemEqual(remaining) && ItemStack.areItemStackTagsEqual(input, remaining)){
                if(remaining.getCount() == 1){continue;}
                else{
                    remaining.grow(input.getCount() -1);
                    this.craftMatrix.setInventorySlotContents(i, remaining);
                    sendPacket(i, remaining);
                }
            }

            this.craftMatrix.decrStackSize(i, 1);
            input = this.craftMatrix.getStackInSlot(i);
            if(remaining.isEmpty()){
                sendPacket(i, input);
                continue;
            } else if(input.isEmpty()){
                this.craftMatrix.setInventorySlotContents(i, remaining);
                sendPacket(i, remaining);
                continue;
            } else if (!this.player.inventory.addItemStackToInventory(remaining)) {
                this.player.dropItem(remaining, false);
            }
            sendPacket(i, input);
        }
        return stack;
    }

    //Helper
    private void sendPacket(int id, ItemStack stack){
        ((EntityPlayerMP) this.player).connection.sendPacket(new SPacketSetSlot(this.container.windowId, id, stack));
    }
}
