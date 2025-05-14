package roidrole.patternbanners.core;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static roidrole.patternbanners.config.Config.config;


@IFMLLoadingPlugin.Name("PatternBannersCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
//Sorting index is, so far, a random number > 1000
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class PatternBannersCore implements IFMLLoadingPlugin, IEarlyMixinLoader {
    static final String mixinCategory = "general.mixins";
    static final ImmutableMap<String, String> mixinConfigs = new ImmutableMap.Builder<String, String>()
            .put("mixins.patternbanners.json", "Delete default recipe")
    .build();

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return mixinConfigs.keySet().asList();
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig){
        return config.get(mixinCategory, mixinConfigs.get(mixinConfig), true).getBoolean();
    }
}
