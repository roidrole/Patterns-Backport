package roidrole.patternbanners;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import roidrole.patternbanners.item.ItemPattern;
import roidrole.patternbanners.proxy.CommonProxy;


@Mod(modid = PatternBanners.MODID, name = PatternBanners.NAME, version = PatternBanners.VERSION)
public class PatternBanners {
    //Constants
    public static final String MODID = "patternbanners";
    public static final String NAME = "Pattern Banners Backport";
    public static final String VERSION = "0.0.1";
    public static Item pattern = new ItemPattern();

    //Proxy
    @SidedProxy(clientSide = "roidrole."+MODID+".proxy.ClientProxy", serverSide = "roidrole."+MODID+".proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {PROXY.preInit();}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {PROXY.init();}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {PROXY.postInit();}
}
