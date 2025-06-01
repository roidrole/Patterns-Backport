package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.config.ConfigMapping;

import java.io.IOException;
import java.util.Objects;

import static roidrole.patternbanners.PatternBanners.pattern;

public class Utils {
    @SideOnly(Side.CLIENT)
    public static boolean itemModelExists(String modid, String modelName) {
        ResourceLocation resLoc = new ResourceLocation(modid, "models/item/" + modelName + ".json");
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(resLoc) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isDye(ItemStack stack){
        if (stack.isEmpty()){return false;}
        for(int oreID : OreDictionary.getOreIDs(stack)){
            if(OreDictionary.getOreName(oreID).startsWith("dye")){return true;}
        }
        return false;
    }

    public static int getDyeColor(ItemStack dye){
        for(int oreID : OreDictionary.getOreIDs(dye)){
            String oreName = OreDictionary.getOreName(oreID);
            if(oreName.startsWith("dye") && !oreName.equals("dye")){
                if(oreName.equals("dyeLightGray")){return 7;}
                if(oreName.equals("dyeLightBlue")){return 12;}
                return EnumDyeColor.valueOf(oreName.substring(3).toUpperCase()).getDyeDamage();
            }
        }
        return -1;
    }

    public static ItemStack getItemStack(ConfigCategory mapping){
        if(!mapping.containsKey("item")){return ItemStack.EMPTY;}
        String[] params = mapping.get("item").getString().split(":");
        if(params.length == 2){return new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1]))), 1, 0);}
        if(params.length == 3){return new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1]))), 1, Integer.parseInt(params[2]));}
        return ItemStack.EMPTY;
    }

    public static boolean isPatternItem(ItemStack stack){
        if(stack.getItem().equals(pattern)){return true;}
        for(ItemStack entry : ConfigMapping.extraPatternItems){
            if(entry.getItem().equals(stack.getItem()) && entry.getItemDamage() == stack.getItemDamage()){return true;}
        }
        return false;
    }


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
