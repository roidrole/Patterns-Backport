package roidrole.patternbanners.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.item.ItemModelMapper;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public void preInit(){
        super.preInit();
        ItemModelMapper.preInit();
    }
}