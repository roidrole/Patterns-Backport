package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;

import javax.annotation.Nonnull;


public class PatternApply extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public ItemStack patternI;
    String patternH;
    public String patternN;

    public PatternApply(ConfigCategory mapping){
        ItemStack patternStack;
        if(mapping.containsKey("uses")){
            patternStack = Utils.getItemStack(mapping, "uses");
            ConfigMapping.extraPatternItems.add(patternStack);
        }else{
            patternStack = new ItemStack(PatternBanners.pattern, 1, mapping.get("meta").getInt());
        }

        this.patternI = patternStack;
        this.patternH = mapping.get("hash").getString();
        if(mapping.containsKey("name")){
            this.patternN = mapping.get("name").getString();
        }
        this.setRegistryName(Tags.MOD_ID, "recipes/pattern_apply/" + patternH);
    }

    @Override
    public boolean matches(InventoryCrafting inv, @Nonnull World worldIn) {
        if(inv.getStackInSlot(0).getItem() != Items.BANNER){return false;}
        if(!inv.getStackInSlot(1).isItemEqual(patternI)){return false;}
        if(TileEntityBanner.getPatterns(inv.getStackInSlot(0)) >= ConfigGeneral.max_banner_layer - 1){return false;}
        return Utils.isDye(inv.getStackInSlot(2));
    }

    @Override
    public @Nonnull ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack output = inv.getStackInSlot(0).copy();
            output.setCount(1);
            Utils.addPattern(output, Utils.getDyeColor(inv.getStackInSlot(2)), patternH);
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {return width + height >=4;}

    @Override
    public @Nonnull ItemStack getRecipeOutput() {return ItemStack.EMPTY;}

    @Override
    public @Nonnull NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.from(ItemStack.EMPTY,
            ItemStack.EMPTY,
            (ConfigGeneral.recipes.patternApply.consumePattern)?ItemStack.EMPTY:patternI.copy(),
            ForgeHooks.getContainerItem(inv.getStackInSlot(2))
        );
    }

    @Override
    public boolean isDynamic() {return true;}

}
