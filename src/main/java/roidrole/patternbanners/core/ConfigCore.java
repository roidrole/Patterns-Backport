package roidrole.patternbanners.core;

import net.minecraftforge.common.config.Config;
import roidrole.patternbanners.Tags;

@Config(
        modid = Tags.MOD_ID,
        name = "patternbanners/mixin"
)
public class ConfigCore {
    @Config.Comment("Should we remove the vanilla pattern-applying recipe?\nRecommended, but option to disable is here if incompatible")
    @Config.RequiresMcRestart
    public static boolean disableVanillaPatternApply = true;
}
