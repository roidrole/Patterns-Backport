package roidrole.patternbanners.config;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.Tags;

import java.io.File;

@Config(
    modid = Tags.MOD_ID,
    name = Tags.MOD_ID+"/general"
)
public class ConfigGeneral {
    @Config.Comment("Put hashes of custom patterns here. You can find the documentation on the github wiki.")
    @Config.Name("Custom Pattern Hashes")
    @Config.RequiresMcRestart
    public static String[] custom_pattern_hashes = {};

    @Config.Comment("Max number of patterns on a banner.\nVanilla default is 16")
    @Config.RequiresMcRestart
    @Config.Name("Max Banner Layers")
    public static int max_banner_layer = 16;

    public static final PatternCategory patterns = new PatternCategory();
    public static class PatternCategory{
        @Config.Comment("Set to false to disable crafting of all patterns")
        @Config.RequiresMcRestart
        public boolean craftable = true;

        @Config.Comment("Should we generate pattern items for shapes?\nOnly affects /patternbanners:configupdate")
        @Config.RequiresMcRestart
        public boolean shapes_pattern = false;

        @Config.Comment("Set to true for the fallback texture to be the 1.14 texture")
        public boolean fallbackToOld = false;

        @Config.Comment("Set to true to always use fallback texture")
        public boolean forceFallback = false;
    }

    public static final RecipeCategory recipes = new RecipeCategory();
    public static class RecipeCategory{

        @Config.Name("Pattern Apply")
        public final PatternApplyCategory patternApply = new PatternApplyCategory();
        public static class PatternApplyCategory{
            @Config.RequiresMcRestart
            public boolean loom = true;

            @Config.RequiresMcRestart
            public boolean craftingTable = false;

            @Config.Comment("Should the pattern-applying recipe consume the pattern?")
            @Config.RequiresMcRestart
            public boolean consumePattern = false;

            public boolean enabled = loom || craftingTable;
        }

        @Config.Name("Pattern Only Shape")
        @Config.Comment("Concerns the banner patterns with no item nor pattern.\nReadds the vanilla crafting table recipe in crafting table, the scroll bar in loom")
        public final PatternOnlyShapeCategory patternOnlyShape = new PatternOnlyShapeCategory();
        public static class PatternOnlyShapeCategory{
            @Config.RequiresMcRestart
            public boolean loom = true;

            @Config.RequiresMcRestart
            public boolean craftingTable = false;

            public boolean enabled = !ConfigGeneral.patterns.shapes_pattern && (loom || craftingTable);
        }

        @SideOnly(Side.CLIENT)
        @Config.Comment("Determines the layout of the HEI tab:\ntrue: Crafting Table\nfalse: Loom")
        public boolean JEICraftingTable = patternApply.craftingTable || patternOnlyShape.craftingTable;
    }
    @Config.Ignore
    public static Configuration config = new Configuration(new File("config/"+Tags.MOD_ID+"/general.cfg"));

    @Config.Ignore
    public static boolean loom = ConfigGeneral.recipes.patternApply.loom || ConfigGeneral.recipes.patternOnlyShape.loom;
}
