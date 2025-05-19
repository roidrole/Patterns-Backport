package roidrole.patternbanners.config;

import net.minecraft.item.crafting.RecipesBanners;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import roidrole.patternbanners.Tags;

import java.io.File;

@Config(
        modid = Tags.MOD_ID,
        name = Tags.MOD_ID+"/general"
)
public class ConfigGeneral {
    @Config.Ignore
    public static Configuration config = new Configuration(new File("config/"+Tags.MOD_ID+"/general.cfg"));

    @Config.Comment("Put hashes of custom patterns here. You can find the documentation on the github wiki.")
    @Config.Name("Custom Pattern Hashes")
    @Config.RequiresMcRestart
    public static String[] custom_pattern_hashes = {};

    @Config.Comment("Max number of patterns on a banner.\nVanilla default is 16")
    @Config.RequiresMcRestart
    @Config.Name("Max Banner Layers")
    public static int max_banner_layer = 16;

    @Config.Comment("Should we generate pattern items for shapes?")
    public static boolean shapes_pattern = true;

    @Config.Comment("Should we generate recipes for the following tables?")
    public static Recipes recipes = new Recipes();
    public static class Recipes{
        public static boolean loom = true;
        public static boolean craftingTable = false;
    }
}
