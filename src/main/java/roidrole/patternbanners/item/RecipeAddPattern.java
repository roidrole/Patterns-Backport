package roidrole.patternbanners.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.Config;


public class RecipeAddPattern extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if(inv.getStackInSlot(0).getItem() != Items.BANNER){return false;}
        if(inv.getStackInSlot(1).getItem() != roidrole.patternbanners.PatternBanners.pattern){return false;}
        if(inv.getStackInSlot(2).isEmpty()){return false;}
        for(int oreID : OreDictionary.getOreIDs(inv.getStackInSlot(2))){
            if(OreDictionary.getOreName(oreID).startsWith("dye")){return true;}
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        String pattern = "";
        int color = inv.getStackInSlot(2).getItemDamage();
        for(ConfigCategory mapping : Config.mappings){
            if(mapping.get("meta").getInt() == inv.getStackInSlot(1).getItemDamage()){
                pattern = mapping.get("hash").getString();
                break;
            }
        } //Sets pattern
        if(pattern.isEmpty()){return ItemStack.EMPTY;}
        ItemStack output = inv.getStackInSlot(0).copy();
            output.setCount(1);
            addPattern(output, pattern, color);
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {return width >=2 && height >=2;}

    @Override
    public ItemStack getRecipeOutput() {return Items.BANNER.getDefaultInstance();}

    //Helper
    public void addPattern(ItemStack banner, String pattern, int color){
        NBTTagCompound nbt = banner.getOrCreateSubCompound("BlockEntityTag");
        NBTTagList patternList;
        if (nbt.hasKey("Patterns", 9)){patternList = nbt.getTagList("Patterns", 10);}
        else{patternList = new NBTTagList(); nbt.setTag("Patterns", patternList);}
        NBTTagCompound patternToAdd = new NBTTagCompound();
            patternToAdd.setString("Pattern", pattern);
            patternToAdd.setInteger("Color", color);
        patternList.appendTag(patternToAdd);
    }
}
