package com.yongaishide.chaosworld.mixin.ae2;

import appeng.blockentity.storage.IOPortBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = IOPortBlockEntity.class, remap = false)
public class IOPortBlockEntityMixin {

    @ModifyConstant(method = "tickingRequest", constant = @Constant(longValue = 256L), remap = false)
    private long chaosworld_ioPortSpeed(long original) {
        return 100000L;
    }
}
