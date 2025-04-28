package roidrole.patternbanners;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.util.Set;

import static roidrole.patternbanners.PatternBanners.MODID;

public class Config {
    public static Configuration config = new Configuration(new File("config/"+MODID+".cfg"));
    public static ConfigCategory mappingCategory = config.getCategory("mappings");
    public static ConfigCategory generalCategory = config.getCategory("general");
    public static Set<ConfigCategory> mappings = mappingCategory.getChildren();
    public static boolean generated = !mappings.isEmpty();

    public static void preInit(){
        Property max_banner_layer = new Property("max_banner_layer", "16", Property.Type.INTEGER);
        max_banner_layer.setComment("Maximal number of patterns on a single banner");
        generalCategory.put("max_banner_layer", max_banner_layer);

        Property shapes_pattern = new Property("shapes_pattern", "false", Property.Type.BOOLEAN);
        shapes_pattern.setComment("Should we generate pattern for non-item patterns (such as square corner)?");
        generalCategory.put("shapes_pattern", shapes_pattern);
    }
    public static void postInit(){
        if(!generated) {
            generateMappings();
            config.save();
        }
    }

    public static void generateMappings(){
        config.load();
        mappingCategory.setComment(
            "Controls the mapping of metadata -> pattern.\n"+
            "Impacts the item's damage value, texture and name\n"+
            "To regenerate, use /configregen.\n" +
            "You shouldn't touch this unless you know what you're doing."
        );
        ConfigCategory temp;
        for (BannerPattern pattern : BannerPattern.values()) {
            if(pattern.hasPatternItem()){
                temp = new ConfigCategory(String.valueOf(pattern.ordinal()), mappingCategory);
                temp.put("hash", new Property("hash", pattern.getHashname(), Property.Type.STRING));
                temp.put("name", new Property("name", pattern.getFileName(), Property.Type.STRING));
                temp.put("meta", new Property("meta", String.valueOf(pattern.ordinal()), Property.Type.INTEGER));
                ItemStack stack = pattern.getPatternItem();
                String item = String.join(":", stack.getItem().getRegistryName().toString(), String.valueOf(stack.getItemDamage()));
                temp.put("item", new Property("item", item, Property.Type.STRING));
            } else if (generalCategory.get("shapes_pattern").getBoolean() && pattern.ordinal() != 0) {
                temp = new ConfigCategory(String.valueOf(pattern.ordinal()), mappingCategory);
                temp.put("hash", new Property("hash", pattern.getHashname(), Property.Type.STRING));
                temp.put("name", new Property("name", pattern.getFileName(), Property.Type.STRING));
                temp.put("meta", new Property("meta", String.valueOf(pattern.ordinal()), Property.Type.INTEGER));
                String shap = ("."+String.join("", pattern.getPatterns())+".");
                temp.put("shap", new Property("shap", shap, Property.Type.STRING));
            }
        }
    }

    public static ConfigCategory getMappingFor(int meta){return config.getCategory("mappings."+meta);}

    public static ItemStack getItemStack(ConfigCategory mapping){
        if(!generated){return ItemStack.EMPTY;}
        String[] params = mapping.get("item").getString().split(":");
        if(params.length != 3){return ItemStack.EMPTY;}
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1])), 1, Integer.parseInt(params[2]));
    }
}
