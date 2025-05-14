package roidrole.patternbanners.config;

import net.minecraftforge.common.config.Config;
import roidrole.patternbanners.Tags;

@Config(
        modid = Tags.MOD_ID,
        name = "patternbanners/general"
)
public class ConfigGeneral {
    @Config.Comment("Put hashes of custom patterns here. You can find the documentation on the github wiki.")
    @Config.RequiresMcRestart
    @Config.Name("Custom Pattern Hashes")
    public static String[] custom_pattern_hashes = {};

    @Config.Comment("Max number of patterns on a banner.\nVanilla default is 16")
    @Config.RequiresMcRestart
    @Config.Name("Max Banner Layers")
    public static int max_banner_layer = 16;

    @Config.Comment("Should we generate pattern items for shapes?")
    @Config.RequiresMcRestart
    public static boolean shapes_pattern = true;
}
