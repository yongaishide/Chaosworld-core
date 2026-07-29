package com.yongaishide.chaosworld.mixin.ae2;

import appeng.helpers.externalstorage.GenericStackInv;
import appeng.api.stacks.AEKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "appeng.menu.implementations.SetStockAmountMenu", remap = false)
public class SetStockAmountMenuMixin {

    @Redirect(
            method = "setWhatToStock",
            at = @At(value = "INVOKE", target = "Lappeng/helpers/externalstorage/GenericStackInv;getMaxAmount(Lappeng/api/stacks/AEKey;)J")
    )
    private long chaosworld_unlimitedMaxAmount(GenericStackInv instance, AEKey key) {
        return Long.MAX_VALUE;
    }
}
