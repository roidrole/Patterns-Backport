package roidrole.patternbanners.proxy;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.Config;
import roidrole.patternbanners.RecipePatternApply;

import static roidrole.patternbanners.PatternBanners.*;

public class CommonProxy {

    public void preInit(){
        ForgeRegistries.ITEMS.register(pattern);
        Config.preInit();
    }

    public void init(){
        for (ConfigCategory mapping : Config.mappings){
            GameRegistry.addShapelessRecipe(
                    new ResourceLocation(MODID, "recipes/pattern_create_" + mapping.get("name").getString()),
                    new ResourceLocation(""),
                    new ItemStack(pattern, 1, mapping.get("meta").getInt()),
                    Ingredient.fromItem(Items.PAPER),
                    Ingredient.fromStacks(Config.getItemStack(mapping))
            );
        }
        for (ConfigCategory mapping : Config.mappings) {
            GameData.register_impl(new RecipePatternApply(mapping).setRegistryName(MODID, "recipes/pattern_apply/" + mapping.get("hash").getString()));
        }
    }

    public void postInit(){
        Config.postInit();
    }
}