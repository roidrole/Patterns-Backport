package roidrole.patternbanners.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.Config;
import roidrole.patternbanners.Utils;

import java.util.Map.Entry;

import static roidrole.patternbanners.PatternBanners.*;

public class ItemPattern extends Item {
    public ItemPattern() {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setHasSubtypes(true);
        this.setRegistryName(MODID, "pattern");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item." + MODID + ".pattern.name", getPatternLang(stack));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)){
            for (Entry<String, Property> configEntry : Config.config.getCategory(Config.mappings).entrySet()){
                items.add(new ItemStack(this, 1,Integer.parseInt(configEntry.getKey())));
            }
        }
    }

    public String getPatternLang(ItemStack stack){
        String key = Utils.getPatternFromInt(stack.getItemDamage()).toLowerCase();
        if (I18n.hasKey(MODID + ".pattern."+key+".name")){return I18n.format(MODID + ".pattern."+key+".name");}
        else{return Utils.snakeToItem(key);}
    }
}