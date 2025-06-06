package roidrole.patternbanners.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@IFMLLoadingPlugin.Name("PatternBannersCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
//Sorting index is, so far, a random number > 1000
@IFMLLoadingPlugin.SortingIndex(1521)
@IFMLLoadingPlugin.TransformerExclusions("roidrole.patternbanners.core")
public class PatternBannersCore implements IFMLLoadingPlugin, IEarlyMixinLoader {

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
        List<String> mixinconfs = new ArrayList<>(2);
        if(ConfigCore.wearBannerOnHead){mixinconfs.add("mixins.patternbanners.wear_banners.json");}
        if(ConfigCore.no_vanilla_pattern_apply){mixinconfs.add("mixins.patternbanners.no_vanilla_pattern_apply.json");}
        return mixinconfs;
    }
}

