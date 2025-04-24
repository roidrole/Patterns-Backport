package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.Config;

import static roidrole.patternbanners.PatternBanners.pattern;

public class CommonProxy {

    public void preInit(){ForgeRegistries.ITEMS.register(pattern);}

    public void init(){}

    public void postInit(){
        Config.postInit();
    }
}