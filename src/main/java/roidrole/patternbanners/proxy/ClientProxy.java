package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.config.ConfigGeneral;
import roidrole.patternbanners.item.ItemModelMapper;
import roidrole.patternbanners.loom._Loom;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(){
        super.preInit();
        ItemModelMapper.preInit();
        if(ConfigGeneral.loom){
            _Loom.preInitClient();
        }
    }
}