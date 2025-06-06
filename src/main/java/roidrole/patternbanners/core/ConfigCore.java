package roidrole.patternbanners.core;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import roidrole.patternbanners.Tags;

@Config(
        modid = Tags.MOD_ID,
        name = "patternbanners/mixin"
)
public class ConfigCore {
    @Config.Comment("Should we remove the vanilla pattern-applying recipe?\nRecommended, but option to disable is here if incompatible")
    @Config.RequiresMcRestart
    public static boolean no_vanilla_pattern_apply = true;

    @Config.Comment("Should players be able to wear banners on their head?")
    @Config.RequiresMcRestart
    public static boolean wearBannerOnHead = true;

    static {
        ConfigAnytime.register(ConfigCore.class);
    }
}

//TODO:Map markers?