package com.yongaishide.chaosworld.mixin.ae2;

import appeng.me.service.StorageService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StorageService.class, remap = false)
public abstract class StorageServiceMixin {

    @Shadow
    protected abstract void updateCachedStacks();

    /**
     * @author chaosworld
     * @reason run storage update every tick for max speed
     */
    @Overwrite(remap = false)
    public void onServerEndTick() {
        updateCachedStacks();
    }
}
