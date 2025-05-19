package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
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

import static roidrole.patternbanners.PatternBanners.pattern;

public class _Recipe {
    public static String categoryUid = Tags.MOD_ID+"pattern";
    public static final List<PatternApply> LOOM_RECIPES = new ArrayList<>();

    public static void init(){
        for (ConfigCategory mapping : ConfigMapping.mappings){
            ResourceLocation name = new ResourceLocation(Tags.MOD_ID, "recipes/pattern_create_" + mapping.get("hash").getString());
            ResourceLocation group = new ResourceLocation("");
            ItemStack patternStack = new ItemStack(pattern, 1, mapping.get("meta").getInt());
            if(mapping.containsKey("item") && !mapping.get("item").getString().isEmpty()){
                GameRegistry.addShapelessRecipe(name, group, patternStack,
                        Ingredient.fromItem(Items.PAPER),
                        Ingredient.fromStacks(Utils.getItemStack(mapping))
                );
            } else if (mapping.containsKey("shap") && mapping.get("shap").getString().length() == 11) {
                GameData.register_impl(new PatternFromShape(mapping).setRegistryName(Tags.MOD_ID, "recipes/pattern_create_/" + mapping.get("hash").getString()));
            }
        }
        for (ConfigCategory mapping : ConfigMapping.mappings) {
            PatternApply nextOne = new PatternApply(mapping);
            if(ConfigGeneral.Recipes.loom){
                LOOM_RECIPES.add(nextOne);
            }
            if(ConfigGeneral.Recipes.craftingTable) {
                GameData.register_impl(nextOne.setRegistryName(Tags.MOD_ID, "recipes/pattern_apply/" + mapping.get("hash").getString()));
            }
        }
    }
}
