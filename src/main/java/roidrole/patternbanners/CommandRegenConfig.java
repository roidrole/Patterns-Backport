package roidrole.patternbanners;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.ConfigCategory;

import static roidrole.patternbanners.Config.*;

public class CommandRegenConfig extends CommandBase {
    @Override
    public String getName() {
        return "configregen";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/patternbanners configregen - Regenerates pattern mappings";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        for(ConfigCategory child : mappings){
            config.removeCategory(child);
        }
        Config.generateMappings();
        //TODO:Figure out where that 0 comes from and if it can not be added
        config.removeCategory(Config.getMappingFor(0));
        config.save();
        config.load();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
}