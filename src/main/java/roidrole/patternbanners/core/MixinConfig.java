package roidrole.patternbanners.core;

import net.minecraftforge.common.config.Config;
import roidrole.patternbanners.Tags;

@Config(
        modid = Tags.MOD_ID,
        name = "patternbanners/mixin"
)
public class MixinConfig {
    @Config.Comment("Should we remove the vanilla pattern-applying recipe?")
    @Config.RequiresMcRestart
    public static boolean disableVanillaPatternApply = true;
}
