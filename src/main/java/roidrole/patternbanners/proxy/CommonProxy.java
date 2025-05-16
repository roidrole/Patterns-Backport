package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;
import roidrole.patternbanners.integration._Integration;
import roidrole.patternbanners.recipe._Recipe;

import static roidrole.patternbanners.PatternBanners.pattern;


public class CommonProxy {

    public void preInit(){
        ConfigMapping.config.load();
        ConfigMapping.preInit();
        ForgeRegistries.ITEMS.register(pattern);
    }

    public void init(){
        ConfigGeneral.config.load();
        ConfigMapping.init();
        _Integration.init();
        _Recipe.init();
    }

    public void postInit(){
        ConfigMapping.postInit();
    }
}