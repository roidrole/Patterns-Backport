package roidrole.patternbanners;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

import static roidrole.patternbanners.PatternBanners.MODID;

public class Config {
    public static Configuration config = new Configuration(new File("config/"+MODID+".cfg"));
    public static String mappings = "Banner Pattern Mapping";
    public static boolean generated = !config.getCategory(mappings).isEmpty();

    public static void postInit(){
        if(!generated) {
            generateMappings();
        }
    }

    public static void generateMappings(){
        config.addCustomCategoryComment(mappings,
    "Controls the mapping of metadata -> pattern.\n"+
            "Impacts both the item's damage value and texture\n"+
            "To regenerate, remove this text and the category.\n" +
            "You shouldn't touch this unless you know what you're doing."
        );

        for (BannerPattern pattern : BannerPattern.values()) {
            if(pattern.hasPatternItem()){
                String ordinalS = String.valueOf(pattern.ordinal());
                config.getCategory(mappings).put(ordinalS,new Property(ordinalS, pattern.name(), Property.Type.STRING));
            }
        }
        config.save();
    }
}
