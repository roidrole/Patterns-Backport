package roidrole.patternbanners.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map.Entry;

import static roidrole.patternbanners.PatternBanners.*;
import static roidrole.patternbanners.Utils.itemModelExists;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public void preInit(){
        super.preInit();
        //Maps items to models
        //TODO:Fix or remove
        if (config.getCategory(configMappings).isEmpty()){
            ModelLoader.setCustomModelResourceLocation(pattern, OreDictionary.WILDCARD_VALUE, new ModelResourceLocation(MODID+":pattern", "inventory"));
        }else{
            for (Entry<String, Property> configEntry : config.getCategory(configMappings).entrySet()){
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