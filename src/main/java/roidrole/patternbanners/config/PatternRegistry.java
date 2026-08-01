package roidrole.patternbanners.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.integration._Integration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatternRegistry {
    public static File jsonFile = new File("config/"+ Tags.MOD_ID+"/mappings.json");
    public static Configuration config = new Configuration(new File("config/"+ Tags.MOD_ID+"/mappings.cfg"));
    public static List<ItemStack> extraPatternItems = new ArrayList<>(0);
    public static Int2ObjectMap<PatternData> mappingsMap = new Int2ObjectRBTreeMap<>();

    public static void init(){
        readPatternFile();
        if(mappingsMap.isEmpty()){
            readOldConfig();
        }
        for(String hash : ConfigGeneral.custom_pattern_hashes){
            _Integration.addPattern(hash, hash);
        }
    }

    public static void postInit(){
        if(mappingsMap.isEmpty()){
            for(BannerPattern pattern : BannerPattern.values()){
                if(pattern.ordinal() == 0){
                    continue;
                }
                if(ArrayUtils.contains(ConfigGeneral.custom_pattern_hashes, pattern.getHashname())){
                    PatternData.genMappingFor(pattern);
                    continue;
                }
                if(!ConfigGeneral.patterns.shapes_pattern && !pattern.hasPatternItem() && pattern.hasPattern()){
                    continue;
                }
                PatternData.genMappingFor(pattern);
            }
            writePatternFile();
        }
    }

    public static void readPatternFile() {
        try(JsonReader reader = new JsonReader(new FileReader(jsonFile))){
            reader.setLenient(true);
            reader.beginArray();
            while(reader.peek() == JsonToken.BEGIN_OBJECT){
                PatternData pattern;
                try {
                    pattern = PatternData.readMapping(reader);
                } catch (Exception e){
                    PatternBanners.LOGGER.error("Invalid mapping!", e);
                    while(reader.peek() != JsonToken.END_OBJECT){
                        reader.skipValue();
                    }
                    reader.endObject();
                    continue;
                }
                mappingsMap.put(pattern.meta, pattern);
            }
            reader.endArray();
        } catch (FileNotFoundException e) {
            //No-OP
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ///@return If the operation was a success
    public static boolean writePatternFile() {
        try(JsonWriter writer = new JsonWriter(new FileWriter(jsonFile))){
            writer.setIndent("\t");
            writer.beginArray();
            for (PatternData pattern : mappingsMap.values()) {
                pattern.writeMapping(writer);
            }
            writer.endArray();
            return true;
        } catch (IOException e) {
            PatternBanners.LOGGER.error("Error writing mapping file", e);
            return false;
        }
    }

    /**
     * Attempts to migrate from the old forge config to the new config.
     * Will automatically remove the old file and write the new one on success.
     */
    public static void readOldConfig(){
        File configFile = new File("config/patternbanners/mappings.cfg");
        if(!configFile.exists()){
            return;
        }
        PatternBanners.LOGGER.info("Attmepting to migrate from forge configs to json configs.");
        PatternBanners.LOGGER.info("Reading the forge config mappings.");
        Configuration config = new Configuration(configFile);
        boolean remove = true;
        for(String name : config.getCategoryNames()){
            ConfigCategory mapping = config.getCategory(name);
            PatternData pattern;
            try{
                pattern = PatternData.readMapping(mapping);
            } catch (IOException e) {
                PatternBanners.LOGGER.error("Invalid mapping!", e);
                remove = false;
                continue;
			}
            mappingsMap.put(pattern.meta, pattern);
        }
        PatternBanners.LOGGER.info("Forge config mapping file has been read.");
        if(!writePatternFile()){
            PatternBanners.LOGGER.error("Cannot write the new configuration file. See error above. Retrying next launch.");
        } else if(!remove){
            PatternBanners.LOGGER.warn("Not removing the forge config mapping file because there was an error reading it.");
        } else if(!configFile.delete()){
            PatternBanners.LOGGER.error("Error deleting the old forge config. You may delete it manually. We won't retry.");
        } else {
            PatternBanners.LOGGER.info("Successfully migrated mappings from forge config to json.");
        }
    }

    public static int getDamageFromHash(String hash){
        for(PatternData data : mappingsMap.values()){
            if(Objects.equals(data.hash, hash)){
                return data.meta;
            }
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public static String getPatternLang(ItemStack stack){
        int meta = stack.getItemDamage();
        if(!mappingsMap.containsKey(meta)){
            return String.valueOf(meta);
        }
        String name = mappingsMap.get(meta).name;
        String key = Tags.MOD_ID + ".pattern."+name+".name";
        if (I18n.hasKey(key)){
            return I18n.format(key);
        } else{
            StringBuilder out = new StringBuilder();
            for(String word : name.toLowerCase().split("_")){
                out.append(" ");
                out.append(StringUtils.capitalize(word));
            }
            return out.substring(1);
        }
    }

}
