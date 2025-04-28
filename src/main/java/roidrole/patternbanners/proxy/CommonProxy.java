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
import roidrole.patternbanners.recipe.*;

import static roidrole.patternbanners.PatternBanners.*;


public class CommonProxy {

    public void preInit(){
        ForgeRegistries.ITEMS.register(pattern);
        Config.preInit();
    }

    public void init(){
        for (ConfigCategory mapping : Config.mappings){
            ResourceLocation name = new ResourceLocation(MODID, "recipes/pattern_create_" + mapping.get("hash").getString());
            ResourceLocation group = new ResourceLocation("");
            ItemStack patternStack = new ItemStack(pattern, 1, mapping.get("meta").getInt());
            if(mapping.containsKey("item")){
                GameRegistry.addShapelessRecipe(name, group, patternStack,
                    Ingredient.fromItem(Items.PAPER),
                    Ingredient.fromStacks(Config.getItemStack(mapping))
                );
            } else if (mapping.containsKey("shap")) {
                GameData.register_impl(new PatternFromShape(mapping).setRegistryName(MODID, "recipes/pattern_create_/" + mapping.get("hash").getString()));
            }
        }
        for (ConfigCategory mapping : Config.mappings) {
            GameData.register_impl(new PatternApply(mapping).setRegistryName(MODID, "recipes/pattern_apply/" + mapping.get("hash").getString()));
        }
    }

    public void postInit(){
        Config.postInit();
    }
}