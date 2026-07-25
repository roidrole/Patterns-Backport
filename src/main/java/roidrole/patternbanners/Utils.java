package roidrole.patternbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.patternbanners.config.PatternRegistry;

import java.io.IOException;

import static roidrole.patternbanners.PatternBanners.pattern;

public class Utils {
    @SideOnly(Side.CLIENT)
    public static boolean itemModelExists(String modid, String modelName) {
        ResourceLocation resLoc = new ResourceLocation(modid, "models/item/" + modelName + ".json");
        try {
            Minecraft.getMinecraft().getResourceManager().getResource(resLoc);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isDye(ItemStack stack){
        if (stack.isEmpty()){return false;}
        for(int oreID : OreDictionary.getOreIDs(stack)){
            if(OreDictionary.getOreName(oreID).startsWith("dye")){return true;}
        }
        return false;
    }

    public static int getDyeColor(ItemStack dye){
        for(int oreID : OreDictionary.getOreIDs(dye)){
            String oreName = OreDictionary.getOreName(oreID);
            if(oreName.startsWith("dye") && !oreName.equals("dye")){
                if(oreName.equals("dyeLightGray")){return 7;}
                if(oreName.equals("dyeLightBlue")){return 12;}
                return EnumDyeColor.valueOf(oreName.substring(3).toUpperCase()).getDyeDamage();
            }
        }
        return -1;
    }

    public static boolean isPatternItem(ItemStack stack){
        if(stack.getItem().equals(pattern)){
            return true;
        }
        for(ItemStack entry : PatternRegistry.extraPatternItems){
            if(entry.getItem().equals(stack.getItem()) && entry.getItemDamage() == stack.getItemDamage()){return true;}
        }
        return false;
    }


    public static void addPattern(ItemStack banner, int color, String patternHash){
        NBTTagCompound nbt = banner.getOrCreateSubCompound("BlockEntityTag");
        NBTTagList patternList;
        if (nbt.hasKey("Patterns", 9)){
            patternList = nbt.getTagList("Patterns", 10);
        }
        else{
            patternList = new NBTTagList();
            nbt.setTag("Patterns", patternList);
        }
        NBTTagCompound patternToAdd = new NBTTagCompound();
        patternToAdd.setString("Pattern", patternHash);
        patternToAdd.setInteger("Color", color);
        patternList.appendTag(patternToAdd);
    }

    public static ItemStack parseItemStack(String itemString) throws IOException{
        String[] itemStringSplit = itemString.split(":");
        if(itemStringSplit.length == 0 || itemStringSplit.length == 1){
            throw new IOException("An item entry must have at least one :. The provided entry "+String.join(":", itemStringSplit)+" doesn't");
        }
        Item item = Item.REGISTRY.getObject(new ResourceLocation(itemStringSplit[0], itemStringSplit[1]));
        if(item == null){
            throw new IOException("Tried to create a mapping with an unregisted item: "+String.join(":", itemStringSplit));
        }
        int itemMeta;
        if(itemStringSplit.length == 2){
            itemMeta = 0;
        } else if(itemStringSplit.length == 3){
            itemMeta = Integer.parseInt(itemStringSplit[2]);
        } else {
            throw new IOException("Items may have at most 2 :. Tried to register "+String.join(":", itemStringSplit));
        }
        return new ItemStack(item, 1, itemMeta);
    }

    public static String writeItemStack(ItemStack stack){
        return stack.getItem().delegate.name().toString() + ":" + stack.getItemDamage();
    }
}
