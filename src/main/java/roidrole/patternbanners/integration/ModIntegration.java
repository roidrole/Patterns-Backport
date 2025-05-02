package roidrole.patternbanners.integration;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;

import static roidrole.patternbanners.config.Config.config;

public class ModIntegration {
    public static void init(){
        if(Loader.isModLoaded("deeperdepths") && checkIntegration("deeperdepths")){
            DeeperDepths.init();
        }
        if(checkIntegration("cartographer")){
            Cartographer.init();
        }
    }

    //Helpers
    public static void addPattern(String hash, String name){
        EnumHelper.addEnum(BannerPattern.class, hash.toUpperCase(), new Class[]{String.class, String.class}, name, hash);
    }

    public static boolean checkIntegration(String integration){
        return config.get("integration", integration, true).getBoolean();
    }
}
