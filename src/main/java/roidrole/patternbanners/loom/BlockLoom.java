package roidrole.patternbanners.loom;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roidrole.patternbanners.Tags;

import static roidrole.patternbanners.PatternBanners.INSTANCE;
import static roidrole.patternbanners.GuiHandler.guiLoomID;

public class BlockLoom extends Block {
    public BlockLoom() {
        super(Material.WOOD);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(Tags.MOD_ID, "loom");
        this.setTranslationKey(Tags.MOD_ID+".loom");
        this.setHardness(2.5F);
        this.setResistance(2.5F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(INSTANCE, guiLoomID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTYFACING);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(PROPERTYFACING, EnumFacing.byHorizontalIndex(meta));
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROPERTYFACING).getHorizontalIndex();
    }
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);
        return this.getDefaultState().withProperty(PROPERTYFACING, EnumFacing.byHorizontalIndex(enumfacing.getHorizontalIndex() + 2 % 4));
    }
}
