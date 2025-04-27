package roidrole.patternbanners.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.ConfigCategory;

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
            for (ConfigCategory mapping : Config.mappings){
                int meta = mapping.get("meta").getInt();
                String patternS = mapping.get("name").getString();
                if (itemModelExists(MODID, "pattern/"+patternS)){
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, new ModelResourceLocation(MODID+":pattern/"+patternS, "inventory"));
                }else{
                    ModelLoader.setCustomModelResourceLocation(pattern, meta, new ModelResourceLocation(MODID+":pattern", "inventory"));
                }
            }
        }
    }
}
