package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static roidrole.patternbanners.PatternBanners.pattern;

public class _Recipe {
    public static List<PatternApply> PATTERN_APPLY_RECIPES;
    public static List<BannerPattern> PATTERNS_ONLY_SHAPE = new ArrayList<>();

    public static void init(){
        //Populate registries
        if(ConfigGeneral.recipes.patternOnlyShape.enabled){
            for(BannerPattern pattern : BannerPattern.values()){
                if(pattern.hasPatternItem() || !pattern.hasPattern()){continue;}
                PATTERNS_ONLY_SHAPE.add(pattern);
            }
        }
        if(ConfigGeneral.recipes.patternApply.enabled){
            PATTERN_APPLY_RECIPES = ConfigMapping.mappings.stream().map(PatternApply::new).collect(Collectors.toList());
        }

        if(ConfigGeneral.patterns.craftable){
            for (ConfigCategory mapping : ConfigMapping.mappings){
                ResourceLocation name = new ResourceLocation(Tags.MOD_ID, "recipes/pattern_create/" + mapping.get("hash").getString());
                if(mapping.containsKey("item") && !mapping.get("item").getString().isEmpty()){
                    ResourceLocation group = new ResourceLocation("");
                    ItemStack patternStack = new ItemStack(pattern, 1, mapping.get("meta").getInt());
                    GameRegistry.addShapelessRecipe(name, group, patternStack,
                            Ingredient.fromItem(Items.PAPER),
                            Ingredient.fromStacks(Utils.getItemStack(mapping))
                    );
                } else if (mapping.containsKey("shap") && mapping.get("shap").getString().length() == 11) {
                    GameData.register_impl(new PatternFromShape(mapping).setRegistryName(name));
                }
            }
        }

        if(ConfigGeneral.recipes.patternApply.craftingTable) {
            PATTERN_APPLY_RECIPES.forEach(recipe -> GameData.register_impl(recipe.setRegistryName(Tags.MOD_ID, "recipes/pattern_apply/" + recipe.patternH)));
        }
    }
}
