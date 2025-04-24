package roidrole.patternbanners;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.Property;

import static roidrole.patternbanners.PatternBanners.Config.*;

public class ConfigHandler {
    public static void postInit(){
        if(!generated) {
            generateMappings();
        }
    }

    public static void generateMappings(){
        config.addCustomCategoryComment(mappings,
    "Controls the mapping of metadata -> pattern.\n"+
            "Impacts both the item's damage value and texture, via the patternOrdinal predicate\n"+
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
