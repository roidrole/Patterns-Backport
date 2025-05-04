package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.integration._Integration;
import roidrole.patternbanners.recipe._Recipe;

import static roidrole.patternbanners.PatternBanners.pattern;


public class CommonProxy {

    public void preInit(){
        ForgeRegistries.ITEMS.register(pattern);
        Config.preInit();
    }

    public void init(){
        Config.init();
        _Integration.init();
        _Recipe.init();
    }

    public void postInit(){
        Config.postInit();
    }
}