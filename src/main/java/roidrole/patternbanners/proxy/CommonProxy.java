package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;
import roidrole.patternbanners.integration._Integration;
import roidrole.patternbanners.loom._Loom;
import roidrole.patternbanners.recipe._Recipe;

import static roidrole.patternbanners.PatternBanners.pattern;


public class CommonProxy {

    public void preInit(){
        ConfigMapping.config.load();
        ConfigMapping.preInit();
        ForgeRegistries.ITEMS.register(pattern);
        if(ConfigGeneral.Recipes.loom) {
            _Loom.preInit();
        }
    }

    public void init(){
        ConfigGeneral.config.load();
        ConfigMapping.init();
        _Integration.init();
        _Recipe.init();
        if(ConfigGeneral.Recipes.loom) {
            _Loom.init();
        }
    }

    public void postInit(){
        ConfigMapping.postInit();
    }
}