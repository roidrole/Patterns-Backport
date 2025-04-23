package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static roidrole.patternbanners.PatternBanners.config;
import static roidrole.patternbanners.PatternBanners.configMappings;

public class Utils {
    public static String snakeToItem(String in){
        StringBuilder out = new StringBuilder();
        for(String word : in.toLowerCase().split("_")){
            out.append(" ");
            out.append(StringUtils.capitalize(word));
        }
        return out.substring(1);
    }
    public static String getPatternFromInt(int in){
        try{return config.getCategory(configMappings).get(String.valueOf(in)).getString();}
        catch (Exception e) {return "UNKNOWN";}
    }

    @SideOnly(Side.CLIENT)
    public static boolean itemModelExists(String modid, String modelName) {
        ResourceLocation resLoc = new ResourceLocation(modid, "models/item/" + modelName + ".json");
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(resLoc) != null;
        } catch (IOException e) {
            return false;
        }
    }
}
