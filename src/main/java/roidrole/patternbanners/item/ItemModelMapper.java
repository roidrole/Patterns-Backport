package roidrole.patternbanners.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import roidrole.patternbanners.PatternBanners;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.PatternRegistry;
import roidrole.patternbanners.config.PatternData;

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
        if (ConfigGeneral.patterns.forceFallback || PatternRegistry.mappingsMap.isEmpty()){
            ModelLoader.setCustomMeshDefinition(pattern, (stack -> defaultPatternModel));
            ModelLoader.registerItemVariants(pattern, defaultPatternModel);
        } else {
            ModelLoader.setCustomModelResourceLocation(pattern, 0, defaultPatternModel);
            for(PatternData pattern : PatternRegistry.mappingsMap.values()){
                if(pattern instanceof PatternData.PatternUses){
                    continue;
                }
                if (itemModelExists(Tags.MOD_ID, "pattern/"+pattern)){
                    ModelLoader.setCustomModelResourceLocation(
                        PatternBanners.pattern,
                        pattern.meta,
                        new ModelResourceLocation(Tags.MOD_ID+":pattern/"+pattern.name, "inventory")
                    );
                } else {
                    ModelLoader.setCustomModelResourceLocation(PatternBanners.pattern, pattern.meta, defaultPatternModel);
                }
            }
        }
    }
}
