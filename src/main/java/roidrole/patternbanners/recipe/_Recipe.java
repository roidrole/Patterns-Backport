package roidrole.patternbanners.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.patternbanners.Tags;

import static roidrole.patternbanners.PatternBanners.pattern;

public class _Recipe {
    public static String categoryUid = Tags.MOD_ID+"pattern";

    public static void init(){
        for (ConfigCategory mapping : Config.mappings){
            ResourceLocation name = new ResourceLocation(Tags.MOD_ID, "recipes/pattern_create_" + mapping.get("hash").getString());
            ResourceLocation group = new ResourceLocation("");
            ItemStack patternStack = new ItemStack(pattern, 1, mapping.get("meta").getInt());
            if(mapping.containsKey("item")){
                GameRegistry.addShapelessRecipe(name, group, patternStack,
                        Ingredient.fromItem(Items.PAPER),
                        Ingredient.fromStacks(Config.getItemStack(mapping))
                );
            } else if (mapping.containsKey("shap")) {
                GameData.register_impl(new PatternFromShape(mapping).setRegistryName(Tags.MOD_ID, "recipes/pattern_create_/" + mapping.get("hash").getString()));
            }
        }
        for (ConfigCategory mapping : Config.mappings) {
            GameData.register_impl(new PatternApply(mapping).setRegistryName(Tags.MOD_ID, "recipes/pattern_apply/" + mapping.get("hash").getString()));
        }
    }
}
