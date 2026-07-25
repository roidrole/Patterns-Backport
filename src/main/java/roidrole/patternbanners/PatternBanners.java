package roidrole.patternbanners;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import roidrole.patternbanners.config.CommandUpdateMappings;
import roidrole.patternbanners.config.PatternRegistry;
import roidrole.patternbanners.item.ItemPattern;
import roidrole.patternbanners.proxy.CommonProxy;


@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = PatternBanners.dependencies)
public class PatternBanners {
    //Constants
    public static final String dependencies = "required-after:configanytime;" +
        "required-after:mixinbooter;";
    public static final Item pattern = new ItemPattern();

    @Mod.Instance(Tags.MOD_ID)
    public static PatternBanners INSTANCE;

    //Proxy
    @SidedProxy(clientSide = Tags.ROOT_PACKAGE+".proxy.ClientProxy", serverSide = Tags.ROOT_PACKAGE+".proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static Logger LOGGER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PatternRegistry.postInit();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandUpdateMappings());
    }
}
