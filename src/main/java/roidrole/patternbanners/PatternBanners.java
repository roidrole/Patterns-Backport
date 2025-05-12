package roidrole.patternbanners;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import roidrole.patternbanners.config.CommandUpdateMappings;
import roidrole.patternbanners.item.ItemPattern;
import roidrole.patternbanners.proxy.CommonProxy;


@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class PatternBanners {
    //Constants
    public static Item pattern = new ItemPattern();

    //Proxy
    @SidedProxy(clientSide = Tags.ROOT_PACKAGE+".proxy.ClientProxy", serverSide = Tags.ROOT_PACKAGE+".proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {PROXY.preInit();}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {PROXY.init();}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {PROXY.postInit();}

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandUpdateMappings());
    }
}
