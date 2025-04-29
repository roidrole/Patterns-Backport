package roidrole.patternbanners;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.config.ConfigCategory;

import static roidrole.patternbanners.Config.*;
import static roidrole.patternbanners.PatternBanners.MODID;

public class CommandUpdateMappings extends CommandBase {
    public static String name = MODID+":configupdate";

    @Override
    public String getName() {return name;}

    @Override
    public String getUsage(ICommandSender sender) {
        return "/"+name+" - Regenerates pattern mappings";
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
    }

    @Override
    public int getRequiredPermissionLevel() {return 3;}
}