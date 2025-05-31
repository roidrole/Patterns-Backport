package roidrole.patternbanners;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import roidrole.patternbanners.loom.ContainerLoom;
import roidrole.patternbanners.loom.GuiLoom;

import static roidrole.patternbanners.loom._Loom.loomBlock;

public class GuiHandler implements IGuiHandler {
    public static final int guiLoomID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IBlockState iblockstate = world.getBlockState(new BlockPos(x,y,z));
        if(ID == guiLoomID && (iblockstate.getBlock() == loomBlock))
            return new ContainerLoom(player.inventory, world, new BlockPos(x, y, z));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IBlockState iblockstate = world.getBlockState(new BlockPos(x,y,z));
        if(ID == guiLoomID && (iblockstate.getBlock() == loomBlock))
            return new GuiLoom(new ContainerLoom(player.inventory, world, new BlockPos(x, y, z)));
        return null;
    }
}