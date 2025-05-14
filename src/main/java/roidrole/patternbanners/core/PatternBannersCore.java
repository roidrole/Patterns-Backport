package roidrole.patternbanners.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@IFMLLoadingPlugin.Name("PatternBannersCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
//Sorting index is, so far, a random number > 1000
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
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
        return Collections.singletonList("mixins.patternbanners.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig){
        return MixinConfig.disableVanillaPatternApply;
    }
}

