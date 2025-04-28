package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;

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
}
