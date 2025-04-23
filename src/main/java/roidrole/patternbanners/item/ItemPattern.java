package roidrole.patternbanners.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Property;
import roidrole.patternbanners.Utils;

import java.util.Map.Entry;

import static roidrole.patternbanners.PatternBanners.*;
import static roidrole.patternbanners.PatternBanners.config;

public class ItemPattern extends Item {
    public ItemPattern() {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setHasSubtypes(true);
        this.setRegistryName(MODID, "pattern");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item." + MODID + ".pattern", getPatternLang(stack));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)){
            for (Entry<String, Property> configEntry : config.getCategory(configMappings).entrySet()){
                items.add(new ItemStack(this, 1,Integer.parseInt(configEntry.getKey())));
            }
        }
    }

    public String getPatternLang(ItemStack stack){
        String key = Utils.getPatternFromInt(stack.getItemDamage());
        if (I18n.hasKey(key)){return I18n.format("item." + MODID + ".pattern."+key);}
        else{return Utils.snakeToItem(key);}
    }
}