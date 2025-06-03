package roidrole.patternbanners.config;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.ArrayUtils;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.integration._Integration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigMapping {
    public static Configuration config = new Configuration(new File("config/"+ Tags.MOD_ID+"/mappings.cfg"));
    public static Set<ConfigCategory> mappings = new HashSet<>(8);
    public static List<ItemStack> extraPatternItems = new ArrayList<>(0);

    public static void preInit(){
        for (String name : config.getCategoryNames()){
            mappings.add(config.getCategory(name));
        }
    }

    public static void init(){
        for(String hash : ConfigGeneral.custom_pattern_hashes){
            _Integration.addPattern(hash, hash);
        }
    }

    public static void postInit(boolean client){
        if(config.getCategoryNames().isEmpty()){
            for(BannerPattern pattern : BannerPattern.values()){
                if(pattern.ordinal() == 0){continue;}
                if(ArrayUtils.contains(ConfigGeneral.custom_pattern_hashes, pattern.getHashname())){
                    genMappingFor(pattern, client);
                    continue;
                }
                if(!ConfigGeneral.patterns.shapes_pattern && !pattern.hasPatternItem() && pattern.hasPattern()){continue;}
                genMappingFor(pattern, client);
            }
            config.save();
        }
    }


    //Helpers
    public static void genMappingFor(BannerPattern pattern, boolean client){
        ConfigCategory temp;
        int nextMeta = config.getCategoryNames().size();
        do{nextMeta++;}
        while(config.getCategoryNames().contains(String.valueOf(nextMeta)));
        String meta = String.valueOf(nextMeta);
        if(meta.length() == 1){meta = "0"+meta;}
        temp = config.getCategory(meta);
        temp.put("hash", new Property("hash", pattern.getHashname(), Property.Type.STRING));
        if(client) {
            temp.put("name", new Property("name", pattern.getFileName(), Property.Type.STRING));
        }
        temp.put("meta", new Property("meta", meta, Property.Type.INTEGER));
        if(pattern.hasPatternItem()){
            ItemStack stack = pattern.getPatternItem();
            String item = String.join(":", stack.getItem().getRegistryName().toString(), String.valueOf(stack.getItemDamage()));
            temp.put("item", new Property("item", item, Property.Type.STRING));
        } else if (pattern.hasPattern()) {
            String shap = ("."+String.join("", pattern.getPatterns())+".");
            temp.put("shap", new Property("shap", shap, Property.Type.STRING));
        }
        mappings.add(temp);
    }

    public static int getDamageFromHash(String hash){
        for(String categoryName : config.getCategoryNames()){
            ConfigCategory category = config.getCategory(categoryName);
            if(category.containsKey("hash")){
                if(category.get("hash").getString().equals(hash)){
                    return category.get("meta").getInt();
                }
            }
        }
        return 0;
    }
}
