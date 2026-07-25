package roidrole.patternbanners.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.config.PatternRegistry;
import roidrole.patternbanners.config.PatternData;

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
        return I18n.format("item." + Tags.MOD_ID + ".pattern.name", PatternRegistry.getPatternLang(stack));
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)){
            for (PatternData pattern : PatternRegistry.mappingsMap.values()){
                if(pattern instanceof PatternData.PatternUses){
                    continue;
                }
                items.add(new ItemStack(this, 1, pattern.meta));
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
}