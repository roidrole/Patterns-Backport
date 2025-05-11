package roidrole.patternbanners.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.ConfigCategory;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.patternbanners.Tags;

import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.Utils.itemModelExists;

public class ItemModelMapper {
    public static ModelResourceLocation defaultPatternModel = new ModelResourceLocation(Tags.MOD_ID+":pattern", "inventory");

    public static void preInit(){
        if (!Config.generated){
            ModelLoader.setCustomMeshDefinition(pattern, (stack -> defaultPatternModel));
            ModelLoader.registerItemVariants(pattern, defaultPatternModel);
        }else{
            ModelLoader.setCustomModelResourceLocation(pattern, 0, defaultPatternModel);
            for (ConfigCategory mapping : Config.mappings){
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
