package roidrole.patternbanners;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RecipePatternApply
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe, IRecipeWrapper {

    ConfigCategory mapping;

    public RecipePatternApply(ConfigCategory mapping){
        this.mapping = mapping;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if(inv.getStackInSlot(0).getItem() != Items.BANNER){return false;}
        if(inv.getStackInSlot(1).getItem() != PatternBanners.pattern){return false;}
        if(TileEntityBanner.getPatterns(inv.getStackInSlot(1)) >= Config.generalCategory.get("max_banner_layer").getInt()){return false;}
        if(inv.getStackInSlot(2).isEmpty()){return false;}
        for(int oreID : OreDictionary.getOreIDs(inv.getStackInSlot(2))){
            if(OreDictionary.getOreName(oreID).startsWith("dye")){return true;}
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int color = inv.getStackInSlot(2).getItemDamage();
        ItemStack output = inv.getStackInSlot(0).copy();
            output.setCount(1);
            addPattern(output, color);
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {return width >=2 && height >=2;}

    @Override
    public ItemStack getRecipeOutput() {return Items.BANNER.getDefaultInstance();}

    @Override
    public void getIngredients(IIngredients iIngredients) {
        ItemStack bannerIn = new ItemStack(Items.BANNER, 1, OreDictionary.WILDCARD_VALUE);
        iIngredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(
            Collections.singletonList(bannerIn),
            Collections.singletonList(new ItemStack(PatternBanners.pattern, 1, mapping.get("meta").getInt())),
            OreDictionary.getOres("dye")
        ));
        List<ItemStack> outputList = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            ItemStack bannerOut = new ItemStack(Items.BANNER, 1, i);
            addPattern(bannerOut, 15 - i);
            outputList.add(i, bannerOut);
        }
        iIngredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(outputList));
    }
    //Helper
    public void addPattern(ItemStack banner, int color){
        NBTTagCompound nbt = banner.getOrCreateSubCompound("BlockEntityTag");
        NBTTagList patternList;
        if (nbt.hasKey("Patterns", 9)){patternList = nbt.getTagList("Patterns", 10);}
        else{patternList = new NBTTagList(); nbt.setTag("Patterns", patternList);}
        NBTTagCompound patternToAdd = new NBTTagCompound();
        patternToAdd.setString("Pattern", mapping.get("hash").getString());
        patternToAdd.setInteger("Color", color);
        patternList.appendTag(patternToAdd);
    }
}
