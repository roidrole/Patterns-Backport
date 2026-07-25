package roidrole.patternbanners.recipe;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.PatternData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static roidrole.patternbanners.config.ConfigGeneral.recipes;
import static roidrole.patternbanners.config.PatternRegistry.mappingsMap;

public class _Recipe {
    public static List<PatternApply> PATTERN_APPLY_RECIPES;
    public static List<BannerPattern> PATTERNS_ONLY_SHAPE = new ArrayList<>();

    public static void init(){
        //Populate registries
        if(recipes.patternOnlyShape.enabled){
			Set<String> patternUsingShape = new HashSet<>();
            for(PatternData pattern : mappingsMap.values()){
                if(pattern instanceof PatternData.PatternShape){
                    patternUsingShape.add(pattern.hash);
                }
            }
            for(BannerPattern pattern : BannerPattern.values()){
                if(pattern.hasPatternItem() || !pattern.hasPattern()){
                    continue;
                }
                if(patternUsingShape.contains(pattern.getHashname())){
                    continue;
                }
                PATTERNS_ONLY_SHAPE.add(pattern);
            }
        }
        if(recipes.patternApply.enabled){
            PATTERN_APPLY_RECIPES = mappingsMap.values().stream().map(PatternApply::new).collect(Collectors.toList());
        }

        if(ConfigGeneral.patterns.craftable){
            mappingsMap.values().forEach(PatternData::registerRecipe);
        }

        if(recipes.patternApply.craftingTable) {
            PATTERN_APPLY_RECIPES.forEach(GameData::register_impl);
        }
        if(recipes.patternOnlyShape.craftingTable){
            PATTERNS_ONLY_SHAPE.forEach(pattern -> GameData.register_impl(new PatternOnlyShape(pattern)));
        }
    }
}
