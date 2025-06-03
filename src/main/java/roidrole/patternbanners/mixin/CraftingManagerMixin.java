package roidrole.patternbanners.mixin;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CraftingManager.class)
public abstract class CraftingManagerMixin {
    @Redirect(
        method = "init()Z",
        at = @At(
            value = "INVOKE",
            target = "net.minecraft.item.crafting.CraftingManager.register(Ljava/lang/String;Lnet/minecraft/item/crafting/IRecipe;)V",
            ordinal = 8
        )
    )
    private static void patternbanners_noRegister(String name, IRecipe recipe){
        //NO OP
    }
}
