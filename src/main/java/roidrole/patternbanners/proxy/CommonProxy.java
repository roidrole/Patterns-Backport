package roidrole.patternbanners.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.Config;
import roidrole.patternbanners.item.RecipeAddPattern;

import static roidrole.patternbanners.PatternBanners.MODID;
import static roidrole.patternbanners.PatternBanners.pattern;

public class CommonProxy {

    public void preInit(){ForgeRegistries.ITEMS.register(pattern);}

    public void init(){
        GameData.register_impl(new RecipeAddPattern().setRegistryName(new ResourceLocation(MODID, "recipes/pattern_apply")));
    }

    public void postInit(){
        Config.postInit();
    }
}