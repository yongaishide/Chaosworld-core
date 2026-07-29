package com.yongaishide.chaosworld.mixin.projectexpansion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.math.BigInteger;

@Mixin(targets = "cool.furry.mc.neoforge.projectexpansion.block.entity.BlockEntityTransmutationInterface", remap = false)
public class TransmutationInterfaceItemHandlerMixin {

    @Redirect(
            method = "getMaxCount",
            at = @At(value = "INVOKE", target = "Ljava/math/BigInteger;min(Ljava/math/BigInteger;)Ljava/math/BigInteger;")
    )
    private BigInteger chaosworld_minToLongMax(BigInteger instance, BigInteger configCap) {
        return instance.min(BigInteger.valueOf(Long.MAX_VALUE));
    }

    @Redirect(
            method = "getMaxCount",
            at = @At(value = "INVOKE", target = "Ljava/math/BigInteger;intValue()I")
    )
    private int chaosworld_intValueSafe(BigInteger bigInteger) {
        long val = bigInteger.longValue();
        if (val > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (val < 0) return 0;
        return (int) val;
    }
}
