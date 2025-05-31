package roidrole.patternbanners.loom;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roidrole.patternbanners.GuiHandler;
import roidrole.patternbanners.Tags;

import static roidrole.patternbanners.PatternBanners.INSTANCE;

public class _Loom {
    public static final Block loomBlock = new BlockLoom();
    public static final Item loomItem = new ItemBlock(loomBlock).setRegistryName(Tags.MOD_ID, "loom");

    public static void preInit(){
        ForgeRegistries.BLOCKS.register(loomBlock);
        ForgeRegistries.ITEMS.register(loomItem);
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient(){
        ModelLoader.setCustomModelResourceLocation(loomItem, 0, new ModelResourceLocation(Tags.MOD_ID+":loom"));
    }

    public static void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
    }
}
