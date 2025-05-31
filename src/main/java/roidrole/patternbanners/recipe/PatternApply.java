package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;

import javax.annotation.Nonnull;


public class PatternApply extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public ItemStack patternI;
    String patternH;
    public String patternN;

    public PatternApply(ConfigCategory mapping){
        Item patternItem;
        if(mapping.containsKey("uses")){
            patternItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mapping.get("uses").getString()));
            ConfigMapping.extraPatternItems.add(new ItemStack(patternItem, 1, mapping.get("meta").getInt()));
        }else{patternItem = PatternBanners.pattern;}

        this.patternI = new ItemStack(patternItem, 1, mapping.get("meta").getInt());
        this.patternH = mapping.get("hash").getString();
        this.patternN = mapping.get("name").getString();
    }

    @Override
    public boolean matches(InventoryCrafting inv, @Nonnull World worldIn) {
        if(inv.getStackInSlot(0).getItem() != Items.BANNER){return false;}
        if(!inv.getStackInSlot(1).isItemEqual(patternI)){return false;}
        if(TileEntityBanner.getPatterns(inv.getStackInSlot(0)) >= ConfigGeneral.max_banner_layer){return false;}
        return Utils.isDye(inv.getStackInSlot(2));
    }

    @Override
    public @Nonnull ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack output = inv.getStackInSlot(0).copy();
            output.setCount(1);
            addPattern(output, Utils.getDyeColor(inv.getStackInSlot(2)), patternH);
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
            (ConfigGeneral.Recipes.consumePattern)?ItemStack.EMPTY:patternI.copy(),
            ForgeHooks.getContainerItem(inv.getStackInSlot(2))
        );
    }

    @Override
    public boolean isDynamic() {return true;}

    //Helper
    public static void addPattern(ItemStack banner, int color, String patternHash){
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
        patternToAdd.setString("Pattern", patternHash);
        patternToAdd.setInteger("Color", color);
        patternList.appendTag(patternToAdd);
    }
}
