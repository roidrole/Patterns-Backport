package roidrole.patternbanners.config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;
import roidrole.patternbanners.Tags;

import java.util.Arrays;

import static roidrole.patternbanners.config.Config.*;

public class CommandUpdateMappings extends CommandBase {
    public static String name = Tags.MOD_ID+":configupdate";

    @Override
    public String getName() {return name;}

    @Override
    public String getUsage(ICommandSender sender) {
        return "/"+name+" - Regenerates pattern mappings";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        config.load();
        for (BannerPattern pattern : BannerPattern.values()) {
            if(checkAdd(pattern)){
                genMappingFor(pattern);
            }
        }
        config.save();
        config.load();

        for (ConfigCategory mapping : mappingCategory.getChildren()){
            if(checkRemove(mapping)){config.removeCategory(mapping);}
        }
        config.save();
        config.load();
    }

    @Override
    public int getRequiredPermissionLevel() {return 3;}

    //Helpers
    public static boolean checkAdd(BannerPattern pattern){
        if(pattern.ordinal() == 0){return false;}
        for (ConfigCategory mapping : mappingCategory.getChildren()){
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

        if(Arrays.asList(generalCategory.get("custom_pattern_hashes").getStringList()).contains(hash)){
            return false;
        }
        if(!generalCategory.get("shapes_pattern").getBoolean() && mapping.containsKey("shap")){
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