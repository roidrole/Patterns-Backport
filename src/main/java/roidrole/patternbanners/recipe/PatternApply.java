package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.config.Config;



public class PatternApply extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public ItemStack patternI;
    String patternS;
    public String patternN;

    public PatternApply(ConfigCategory mapping){
        this.patternI = new ItemStack(PatternBanners.pattern, 1, mapping.get("meta").getInt());
        this.patternS = mapping.get("hash").getString();
        this.patternN = mapping.get("name").getString();
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if(inv.getStackInSlot(0).getItem() != Items.BANNER){return false;}
        if(!inv.getStackInSlot(1).isItemEqual(patternI)){return false;}
        if(TileEntityBanner.getPatterns(inv.getStackInSlot(1)) >= Config.generalCategory.get("max_banner_layer").getInt()){return false;}
        return Utils.isDye(inv.getStackInSlot(2));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int color = -1;
        for(int oreID : OreDictionary.getOreIDs(inv.getStackInSlot(2))){
            String oreName = OreDictionary.getOreName(oreID);
            if(oreName.startsWith("dye") && !oreName.equals("dye")){
                color = EnumDyeColor.valueOf(oreName.substring(3).toUpperCase()).getDyeDamage();
                break;
            }
        }
        ItemStack output = inv.getStackInSlot(0).copy();
            output.setCount(1);
            addPattern(output, color);
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {return width >=2 && height >=2;}

    @Override
    public ItemStack getRecipeOutput() {return ItemStack.EMPTY;}
    
    //Helper
    public void addPattern(ItemStack banner, int color){
        NBTTagCompound nbt = banner.getOrCreateSubCompound("BlockEntityTag");
        NBTTagList patternList;
        if (nbt.hasKey("Patterns", 9)){
            patternList = nbt.getTagList("Patterns", 10);
        }
        else{
            patternList = new NBTTagList();
            nbt.setTag("Patterns", patternList);
        }
        NBTTagCompound patternToAdd = new NBTTagCompound();
        patternToAdd.setString("Pattern", patternS);
        patternToAdd.setInteger("Color", color);
        patternList.appendTag(patternToAdd);
    }
}
