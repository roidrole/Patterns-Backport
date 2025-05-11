package roidrole.patternbanners.integration;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import roidrole.patternbanners.config.Config;
import roidrole.patternbanners.patternbanners.Tags;

import static roidrole.patternbanners.PatternBanners.pattern;
import static roidrole.patternbanners.integration._Integration.addPattern;

public class NetherBackport implements _Integration.Integration {

    public void init(){
        addPattern("nb_pig", "piglin");
        MinecraftForge.EVENT_BUS.register(new roidrole.patternbanners.integration.NetherBackport());
    }

    @SubscribeEvent
    public void listen(LootTableLoadEvent event) {
            if (event.getName().toString().startsWith("nb:bastion_hold")) {
                event.getTable().getPool("nb:bastion_hold").addEntry(new LootEntryItem(
                        pattern,
                        9,
                        0,
                        new LootFunction[]{new SetMetadata(new LootCondition[]{}, new RandomValueRange(Config.getDamageFromHash("nb_pig")))},
                        new LootCondition[]{},
                        Tags.MOD_ID+":dd_flo"
                ));
            }
        }
}
