package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.config.ConfigMapping;
import roidrole.patternbanners.item.ItemModelMapper;
import roidrole.patternbanners.loom._Loom;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(){
        super.preInit();
        ItemModelMapper.preInit();
        if(ConfigGeneral.loom.getAsBoolean()){
            _Loom.preInitClient();
        }
    }

    @Override
    public void postInit(){
        //Don't call super here because I want to replace genMappingFor()
        //If other things are to be done in postInit, separate this into another function
        ConfigMapping.postInit(true);
    }
}