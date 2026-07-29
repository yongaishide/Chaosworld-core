package com.yongaishide.chaosworld.mixin.ae2;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.helpers.externalstorage.GenericStackInv;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GenericStackInv.class, remap = false)
public class GenericStackInvMixin {

    @Redirect(
            method = "getMaxAmount",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(JJ)J")
    )
    private long chaosworld_minToMax(long maxStackSize, long capacity) {
        return capacity;
    }
}
