package roidrole.patternbanners.integration;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;

import static roidrole.patternbanners.PatternBanners.MODID;
import static roidrole.patternbanners.config.Config.config;

public class _Integration {
    public static void init(){
        doIntegration(MODID, "cartographer", new Cartographer());
        doIntegration("deeperdepths", "deeperdepths", new DeeperDepths());
        doIntegration("nb" , "unseen_nether_backport", new NetherBackport());
    }

    public interface Integration {
        default void init(){}
    }
    //Helpers
    public static void addPattern(String hash, String name){
        EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), new Class[]{String.class, String.class}, name, hash);
    }

    public static void doIntegration(String modid, String name, Integration integrationClass){
        if(Loader.isModLoaded(modid) && config.get("integration", name, true).getBoolean()){integrationClass.init();}
    }
}
