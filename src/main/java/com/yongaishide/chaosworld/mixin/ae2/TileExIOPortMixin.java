package com.yongaishide.chaosworld.mixin.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.TileExIOPort", remap = false)
public class TileExIOPortMixin {

    @ModifyConstant(method = "tickingRequest", constant = @Constant(longValue = 2048L), remap = false)
    private long chaosworld_exIOPortSpeed(long original) {
        return 4000000L;
    }
}
