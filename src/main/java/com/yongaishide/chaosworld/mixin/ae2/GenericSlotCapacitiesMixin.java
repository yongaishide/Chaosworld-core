package com.yongaishide.chaosworld.mixin.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "appeng.api.behaviors.GenericSlotCapacities", remap = false)
public class GenericSlotCapacitiesMixin {

    @ModifyConstant(method = "<clinit>", constant = @Constant(longValue = 99L), remap = false)
    private static long chaosworld_itemCapacity(long original) {
        return Long.MAX_VALUE;
    }
}
