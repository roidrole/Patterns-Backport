package roidrole.patternbanners.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Property;

import java.util.Map;

import static roidrole.patternbanners.PatternBanners.MODID;
import static roidrole.patternbanners.PatternBanners.pattern;
import roidrole.patternbanners.Config;
import static roidrole.patternbanners.Utils.itemModelExists;

public class ItemModelMapper {
    public static void preInit(){
        if (!Config.generated){
            ModelLoader.setCustomMeshDefinition(pattern, (stack -> new ModelResourceLocation(MODID+":pattern", "inventory")));
            ModelLoader.registerItemVariants(pattern, new ModelResourceLocation(MODID+":pattern", "inventory"));
        }else{
            for (Map.Entry<String, Property> configEntry : Config.config.getCategory(Config.mappings).entrySet()){
                int meta = Integer.parseInt(configEntry.getKey());
                String patternS = configEntry.getValue().getString();
                if (itemModelExists(MODID, "pattern/"+patternS)){
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, new ModelResourceLocation(MODID+":pattern/"+patternS, "inventory"));
                }else{
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, new ModelResourceLocation(MODID+":pattern", "inventory"));
                }
            }
        }
    }
}
