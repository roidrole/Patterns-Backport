package roidrole.patternbanners;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;

import static roidrole.patternbanners.Config.*;

public class CommandRegenConfig extends CommandBase {
    @Override
    public String getName() {return "patternbanners:configregen";}

    @Override
    public String getUsage(ICommandSender sender) {
        return "/patternbanners:configregen - Regenerates pattern mappings";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        config.load();
        //Adds required ones
        boolean addThisPattern = true;
        for (BannerPattern pattern : BannerPattern.values()) {
            for (ConfigCategory mapping : mappingCategory.getChildren()){
                if(!mapping.containsKey("hash")){break;}
                if(pattern.getHashname().equals(mapping.get("hash").getString())){
                    addThisPattern = false;
                    break;
                }
            }
            if(addThisPattern){genMappingFor(pattern);}
        }
        config.removeCategory(Config.getMappingFor(0));

        //Remove redundant ones
        for (ConfigCategory mapping : mappingCategory.getChildren()){
            boolean removeThisPattern = true;
            for (BannerPattern pattern : BannerPattern.values()) {
                if(mapping.get("hash").getString().equals(pattern.getHashname())){
                    removeThisPattern = false;
                    break;
                }
            }
            if(removeThisPattern){config.removeCategory(mapping);}
        }
        config.save();
        config.load();
        /*
        config.load();
        for(ConfigCategory child : mappingCategory.getChildren()){
            config.removeCategory(child);
        }
        Config.generateMappings();
        //TODO:Figure out where that 0 comes from and if it can not be added
        //TODO:Figure out why duplicates some categories
        config.removeCategory(Config.getMappingFor(0));
        config.save();
        config.load();
        */
    }

    @Override
    public int getRequiredPermissionLevel() {return 3;}
}