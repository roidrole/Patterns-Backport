package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class Utils {
    @SideOnly(Side.CLIENT)
    public static boolean itemModelExists(String modid, String modelName) {
        ResourceLocation resLoc = new ResourceLocation(modid, "models/item/" + modelName + ".json");
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(resLoc) != null;
        } catch (IOException e) {
            return false;
        }
    }
}
