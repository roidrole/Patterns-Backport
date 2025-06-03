package roidrole.patternbanners.mixin;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import roidrole.patternbanners.core.ConfigCore;

@Mixin(ItemBanner.class)
public abstract class ItemBannerMixin extends Item {
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        if(ConfigCore.wearBannerOnHead){
            return EntityEquipmentSlot.HEAD;
        }
        return super.getEquipmentSlot(stack);
    }
}
