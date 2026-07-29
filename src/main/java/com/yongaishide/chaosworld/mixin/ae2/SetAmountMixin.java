package com.yongaishide.chaosworld.mixin.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "com.glodblock.github.extendedae.client.gui.subgui.SetAmount", remap = false)
public class SetAmountMixin {

    /**
     * @author chaosworld
     * @reason remove 64 stock cap
     */
    @Overwrite(remap = false)
    private long getMaxAmount() {
        return Long.MAX_VALUE;
    }
}
