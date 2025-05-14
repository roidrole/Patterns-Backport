package roidrole.patternbanners.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.Tags;

import javax.annotation.Nonnull;

import static roidrole.patternbanners.PatternBanners.pattern;

public class ItemPattern extends Item {
    public ItemPattern() {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setHasSubtypes(true);
        this.setRegistryName(Tags.MOD_ID, "pattern");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public @Nonnull String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return I18n.format("item." + Tags.MOD_ID + ".pattern.name", getPatternLang(stack));
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)){
            for (ConfigCategory mapping : Config.mappings){
                if(mapping.containsKey("uses")){continue;}
                items.add(new ItemStack(this, 1,mapping.get("meta").getInt()));
            }
        }
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }


    @Override
    public @Nonnull ItemStack getContainerItem(ItemStack stack) {
        ItemStack container = stack.copy();
        container.setCount(1);
        return container;
    }

    @Override
    public @Nonnull ItemStack getDefaultInstance() {
        return new ItemStack(pattern, 1, 0);
    }

    //Utils
    @SideOnly(Side.CLIENT)
    public String getPatternLang(ItemStack stack){
        String name;
        try {
            name = Config.getMappingFor(stack.getItemDamage()).get("name").getString();
        }catch (Exception e){
            return String.valueOf(stack.getItemDamage());
        }
        String key = Tags.MOD_ID + ".pattern."+name+".name";
        if (I18n.hasKey(key)){return I18n.format(key);}
        else{
            StringBuilder out = new StringBuilder();
            for(String word : name.toLowerCase().split("_")){
                out.append(" ");
                out.append(StringUtils.capitalize(word));
            }
            return out.substring(1);
        }
    }
}