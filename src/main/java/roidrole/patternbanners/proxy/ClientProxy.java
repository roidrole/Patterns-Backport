package roidrole.patternbanners.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map.Entry;


import static roidrole.patternbanners.PatternBanners.Config;
import static roidrole.patternbanners.PatternBanners.MODID;
import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.Utils.itemModelExists;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public void preInit(){
        super.preInit();
        //Maps items to models
        if (!Config.generated){
            ModelLoader.setCustomMeshDefinition(pattern, (stack -> new ModelResourceLocation(MODID+":pattern", "inventory")));
            ModelLoader.registerItemVariants(pattern, new ModelResourceLocation(MODID+":pattern", "inventory"));
        }else{
            for (Entry<String, Property> configEntry : Config.config.getCategory(Config.mappings).entrySet()){
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