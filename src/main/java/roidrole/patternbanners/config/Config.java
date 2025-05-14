package roidrole.patternbanners.config;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.integration._Integration;
import roidrole.patternbanners.Tags;

import java.io.File;
import java.util.Set;

//TODO : move mappings to their own file and make them generate and be used...

public class Config {
    public static Configuration config = new Configuration(new File("config/"+Tags.MOD_ID+"/general.cfg"));

    public static Configuration mappingConfig = new Configuration(new File("config/"+Tags.MOD_ID+"/mappings.cfg"));
    public static ConfigCategory mappingCategory = mappingConfig.getCategory("mappings");
    public static Set<ConfigCategory> mappings = mappingCategory.getChildren();
    public static boolean generated = !mappings.isEmpty();
    public static int patternAmount = mappings.size();

    public static void init(){
        config.load();
        for(String hash : ConfigGeneral.custom_pattern_hashes){
            _Integration.addPattern(hash, hash);
        }
    }
    public static void postInit(){
        if(!generated) {
            generateMappings();
            config.save();
        }
    }

    public static void generateMappings(){
        mappingCategory.setComment(
            "Controls the mapping of metadata -> pattern.\n"+
            "Impacts the item's damage value, texture and name\n"+
            "To update (such as pattern addition/deletion), use /"+ CommandUpdateMappings.name+".\n" +
            "You shouldn't touch this unless you know what you're doing."
        );
        for (BannerPattern pattern : BannerPattern.values()) {
            if(CommandUpdateMappings.checkAdd(pattern)){
                genMappingFor(pattern);
            }
        }
    }
    public static void genMappingFor(BannerPattern pattern){
        ConfigCategory temp;
        do {
            patternAmount += 1;
        } while (mappingConfig.hasCategory(mappingCategory.getName()+Configuration.CATEGORY_SPLITTER+patternAmount));

        temp = new ConfigCategory(String.valueOf(patternAmount), mappingCategory);
        temp.put("hash", new Property("hash", pattern.getHashname(), Property.Type.STRING));
        temp.put("name", new Property("name", pattern.getFileName(), Property.Type.STRING));
        temp.put("meta", new Property("meta", String.valueOf(patternAmount), Property.Type.INTEGER));

        if(pattern.hasPatternItem()){
            ItemStack stack = pattern.getPatternItem();
            String item = String.join(":", stack.getItem().getRegistryName().toString(), String.valueOf(stack.getItemDamage()));
            temp.put("item", new Property("item", item, Property.Type.STRING));
        } else if (pattern.hasPattern()) {
            String shap = ("."+String.join("", pattern.getPatterns())+".");
            temp.put("shap", new Property("shap", shap, Property.Type.STRING));
        }
    }

    public static ConfigCategory getMappingFor(int meta){return config.getCategory("mappings."+meta);}

    public static ItemStack getItemStack(ConfigCategory mapping){
        if(!generated){return ItemStack.EMPTY;}
        String[] params = mapping.get("item").getString().split(":");
        if(params.length != 3){return ItemStack.EMPTY;}
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(params[0], params[1])), 1, Integer.parseInt(params[2]));
    }
    public static int getDamageFromHash(String hash){
        for(ConfigCategory mapping : mappings){
            if(mapping.get("hash").getString().equals(hash)){
                return mapping.get("meta").getInt();
            }
        }
        return 0;
    }
}
