package roidrole.patternbanners.config;

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

    @Config(modid = Tags.MOD_ID, name=Tags.MOD_ID+"/general",category = "general.Pattern Items")
    public static final class Patterns{
        @Config.Comment("Set to false to disable crafting of all patterns")
        @Config.RequiresMcRestart
        public static boolean craftable = true;

        @Config.Comment("Should we generate pattern items for shapes?")
        @Config.RequiresMcRestart
        public static boolean shapes_pattern = true;

        @Config.Comment("Set to true for the fallback texture to be the 1.14 texture")
        public static boolean fallbackToOld = false;

        @Config.Comment("Set to true to always use fallback texture")
        public static boolean forceFallback = false;
    }

    @Config(modid=Tags.MOD_ID, name=Tags.MOD_ID+"/general",category = "general.recipes")
    public static final class Recipes{
        @Config.Comment("Should the loom be loaded and useful?")
        @Config.RequiresMcRestart
        public static boolean loom = true;

        @Config.Comment("Should the crafting table be usable to apply patterns?")
        @Config.RequiresMcRestart
        public static boolean craftingTable = false;

        @Config.Comment("Should the pattern-applying recipe consume the pattern?")
        @Config.RequiresMcRestart
        public static boolean consumePattern = false;
    }
}
