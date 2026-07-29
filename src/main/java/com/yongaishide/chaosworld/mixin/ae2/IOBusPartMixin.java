package com.yongaishide.chaosworld.mixin.ae2;

import appeng.parts.automation.IOBusPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = IOBusPart.class, remap = false)
public class IOBusPartMixin {

    /**
     * @author chaosworld
     * @reason increase operations per tick
     */
    @Overwrite(remap = false)
    protected int getOperationsPerTick() {
        return 10000;
    }
}
