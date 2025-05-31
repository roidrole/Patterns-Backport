package roidrole.patternbanners.config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import roidrole.patternbanners.Tags;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static roidrole.patternbanners.config.ConfigMapping.*;

public class CommandUpdateMappings extends CommandBase {
    public static String name = Tags.MOD_ID+":configupdate";

    @Override
    public @Nonnull String getName() {return name;}

    @Override
    public @Nonnull String getUsage(@Nonnull ICommandSender sender) {return "/"+name+" - Regenerates pattern mappings";}

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        config.load();
        for (BannerPattern pattern : BannerPattern.values()) {
            if(checkAdd(pattern)){
                genMappingFor(pattern);
            }
        }

        for (ConfigCategory mapping : mappings){
            if(checkRemove(mapping)){
                config.removeCategory(mapping);}
        }
        config.save();
    }

    @Override
    public int getRequiredPermissionLevel() {return 3;}

    //Helpers
    public static boolean checkAdd(BannerPattern pattern){
        if(pattern.ordinal() == 0){return false;}
        if(!ConfigGeneral.Patterns.shapes_pattern && !pattern.hasPatternItem() && pattern.hasPattern()){return false;}
        for (ConfigCategory mapping : mappings){
            if(!mapping.containsKey("hash")){continue;}
            if(pattern.getHashname().equals(mapping.get("hash").getString())){
                return false;
            }
        }
        return true;
    }

    public static boolean checkRemove(ConfigCategory mapping){
        if(!mapping.containsKey("hash")){return true;}

        String hash = mapping.get("hash").getString();

        if(hash.equals("b")){return true;}

        if(Arrays.asList(ConfigGeneral.custom_pattern_hashes).contains(hash)){
            return false;
        }
        if(!ConfigGeneral.Patterns.shapes_pattern && mapping.containsKey("shap")){
            return true;
        }
        for (BannerPattern pattern : BannerPattern.values()) {
            if(hash.equals(pattern.getHashname())){
                return false;
            }
        }
        return true;
    }
}