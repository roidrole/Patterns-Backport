package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;

import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.config.ConfigMapping.mappings;

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

    public static ItemStack getItemStack(ConfigCategory mapping){
        if(!mapping.containsKey("item")){return ItemStack.EMPTY;}
        String[] params = mapping.get("item").getString().split(":");
        if(params.length == 2){return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1])), 1, 0);}
        if(params.length == 3){return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1])), 1, Integer.parseInt(params[2]));}
        return ItemStack.EMPTY;
    }

    public static boolean isPatternItem(ItemStack stack){
        if(stack.getItem().equals(pattern)){return true;}
        //TODO:cache list of patternItems
        for(ConfigCategory mapping : mappings){
            if(!mapping.containsKey("uses")){continue;}
            if(mapping.get("meta").getInt() != stack.getItemDamage()){continue;}
            if(stack.getItem().getRegistryName().toString().equals(mapping.get("uses").getString()) && stack.getItemDamage() == mapping.get("meta").getInt()) {
                return true;
            }
        }
        return false;
    }
}
