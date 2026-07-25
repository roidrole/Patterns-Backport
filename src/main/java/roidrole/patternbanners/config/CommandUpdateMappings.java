package roidrole.patternbanners.config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BannerPattern;
import roidrole.patternbanners.Tags;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static roidrole.patternbanners.config.PatternRegistry.mappingsMap;

public class CommandUpdateMappings extends CommandBase {
    public static String name = Tags.MOD_ID+":configupdate";

    @Override
    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String getUsage(@Nonnull ICommandSender sender) {
        return "/"+name+" - Regenerates pattern mappings";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        for (BannerPattern pattern : BannerPattern.values()) {
            if(checkAdd(pattern)){
                PatternData.genMappingFor(pattern);
            }
        }

		mappingsMap.values().removeIf(CommandUpdateMappings::checkRemove);

        PatternRegistry.writePatternFile();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    //Helpers
    public static boolean checkAdd(BannerPattern pattern){
        if(pattern.ordinal() == 0){
            return false;
        }
        if(!ConfigGeneral.patterns.shapes_pattern && !pattern.hasPatternItem() && pattern.hasPattern()){
            return false;
        }
        for (PatternData mapping : mappingsMap.values()){
            if(pattern.getHashname().equals(mapping.hash)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkRemove(PatternData data){
        String hash = data.hash;
        if(hash.equals("b")){
            return true;
        }
        if(Arrays.asList(ConfigGeneral.custom_pattern_hashes).contains(hash)){
            return false;
        }
        if(!ConfigGeneral.patterns.shapes_pattern && data instanceof PatternData.PatternShape){
            return true;
        }
        //TODO: cache
        for (BannerPattern pattern : BannerPattern.values()) {
            if(hash.equals(pattern.getHashname())){
                return false;
            }
        }
        return true;
    }
}