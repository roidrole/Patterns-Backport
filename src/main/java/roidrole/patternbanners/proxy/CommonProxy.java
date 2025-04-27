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
import roidrole.patternbanners.recipe.PatternApply;

import static roidrole.patternbanners.PatternBanners.*;

public class CommonProxy {

    public void preInit(){ForgeRegistries.ITEMS.register(pattern);}

    public void init(){
        //Pattern crafting
        for (ConfigCategory mapping : Config.mappings){
            GameData.register_impl(new PatternApply(mapping).setRegistryName(MODID, "recipes/pattern_apply/"+mapping.get("hash").getString()));
            GameRegistry.addShapelessRecipe(
                    new ResourceLocation(MODID, "recipes/pattern_create_" + mapping.get("name").getString()),
                    new ResourceLocation(""),
                    new ItemStack(pattern, 1, mapping.get("meta").getInt()),
                    Ingredient.fromItem(Items.PAPER),
                    Ingredient.fromStacks(Config.getItemStack(mapping))
            );
        }
    }

    public void postInit(){
        Config.postInit();
    }
}