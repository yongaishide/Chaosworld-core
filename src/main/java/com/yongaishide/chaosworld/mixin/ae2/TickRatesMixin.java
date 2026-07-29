package com.yongaishide.chaosworld.mixin.ae2;

import appeng.core.settings.TickRates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TickRates.class, remap = false)
public class TickRatesMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void chaosworld_tickRates(CallbackInfo ci) {
        TickRates.ImportBus.setMax(10000);
        TickRates.ExportBus.setMax(10000);
        TickRates.StorageBus.setMax(10000);
        TickRates.IOPort.setMax(1000000);
    }
}
