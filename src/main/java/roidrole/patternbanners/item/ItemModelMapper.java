package roidrole.patternbanners.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.ConfigCategory;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;

import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.Utils.itemModelExists;

public class ItemModelMapper {

    public static void preInit(){
        ModelResourceLocation defaultPatternModel;
        if(ConfigGeneral.patterns.fallbackToOld){
            defaultPatternModel = new ModelResourceLocation(Tags.MOD_ID+":pattern_old", "inventory");
        } else {
            defaultPatternModel = new ModelResourceLocation(Tags.MOD_ID+":pattern", "inventory");
        }
        if (ConfigGeneral.patterns.forceFallback || ConfigMapping.config.getCategoryNames().isEmpty()){
            ModelLoader.setCustomMeshDefinition(pattern, (stack -> defaultPatternModel));
            ModelLoader.registerItemVariants(pattern, defaultPatternModel);
        }else{
            ModelLoader.setCustomModelResourceLocation(pattern, 0, defaultPatternModel);
            for (ConfigCategory mapping : ConfigMapping.mappings){
                if(mapping.containsKey("uses")){continue;}
                int meta = mapping.get("meta").getInt();
                String patternS = mapping.get("name").getString();
                if (itemModelExists(Tags.MOD_ID, "pattern/"+patternS)){
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, new ModelResourceLocation(Tags.MOD_ID+":pattern/"+patternS, "inventory"));
                }else{
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, defaultPatternModel);
                }
            }
        }
    }
}
