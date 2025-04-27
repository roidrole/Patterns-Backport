package roidrole.patternbanners;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Set;

import static roidrole.patternbanners.PatternBanners.MODID;

public class Config {
    public static Configuration config = new Configuration(new File("config/"+MODID+".cfg"));
    public static ConfigCategory mappingCategory = config.getCategory("mappings");
    public static Set<ConfigCategory> mappings = mappingCategory.getChildren();
    public static boolean generated = !mappings.isEmpty();
    public static void postInit(){
        if(!generated) {
            generateMappings();
        }
    }

    public static void generateMappings(){
        mappingCategory.setComment(
            "Controls the mapping of metadata -> pattern.\n"+
            "Uses the enum's ordinal and hashValue\n"+
            "Impacts the item's damage value, texture and name\n"+
            "To regenerate, remove this text and the category.\n" +
            "You shouldn't touch this unless you know what you're doing."
        );
        ConfigCategory temp;
        for (BannerPattern pattern : BannerPattern.values()) {
            if(pattern.hasPatternItem()){
                temp = new ConfigCategory(String.valueOf(pattern.ordinal()), mappingCategory);
                temp.put("hash", new Property("hash", pattern.getHashname(), Property.Type.STRING));
                temp.put("name", new Property("name", pattern.getFileName(), Property.Type.STRING));
                temp.put("meta", new Property("meta", String.valueOf(pattern.ordinal()), Property.Type.INTEGER));
            }
        }
        config.save();
    }

    public static ConfigCategory getMappingFor(int meta){return config.getCategory("mappings."+meta);}
    //public static ConfigCategory getMappingFor(String meta){return config.getCategory("mappings."+meta);}
}
