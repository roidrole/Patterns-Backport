package roidrole.patternbanners.integration;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.patternbanners.config.Config;

import java.util.Random;

import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.integration._ModIntegration.addPattern;

public class Cartographer {

    public static void init(){
        ForgeRegistries.VILLAGER_PROFESSIONS
                .getValue(new ResourceLocation("minecraft:librarian"))
                .getCareer(1)
                .addTrade(5, new Cartographer.CartographerTrade());
        addPattern("glo", "globe");
    }

    public static class CartographerTrade implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(pattern, 1, Config.getDamageFromHash("glo"))
            ));
        }
    }

}
