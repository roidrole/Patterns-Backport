package roidrole.patternbanners.integration;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import roidrole.patternbanners.config.Config;

import static roidrole.patternbanners.PatternBanners.MODID;
import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.integration._Integration.addPattern;

public class DeeperDepths implements _Integration.Integration {

    public void init(){
        if(Loader.isModLoaded("deeperdepths")){
            addPattern("dd_flo", "flow");
            addPattern("dd_gust", "guster");
        }
        MinecraftForge.EVENT_BUS.register(new DeeperDepths());
    }

    @SubscribeEvent
    public void listen(LootTableLoadEvent event) {
        if (event.getName().toString().equals("deeperdepths:vault")) {
            event.getTable().getPool("deeperdepths:vault_unique").addEntry(new LootEntryItem(
                    pattern,
                    2,
                    0,
                    new LootFunction[]{new SetMetadata(new LootCondition[]{}, new RandomValueRange(Config.getDamageFromHash("dd_gust")))},
                    new LootCondition[]{},
                    MODID+":dd_flo"
            ));
        } else if (event.getName().toString().equals("deeperdepths:ominous_vault")) {
            event.getTable().getPool("deeperdepths:ominous_vault_unique").addEntry(new LootEntryItem(
                    pattern,
                    2,
                    0,
                    new LootFunction[]{new SetMetadata(new LootCondition[]{}, new RandomValueRange(Config.getDamageFromHash("dd_flo")))},
                    new LootCondition[]{},
                    MODID+":dd_flo"
            ));
        }
    }
}
