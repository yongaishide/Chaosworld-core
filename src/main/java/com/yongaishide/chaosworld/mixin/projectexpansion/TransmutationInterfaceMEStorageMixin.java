package com.yongaishide.chaosworld.mixin.projectexpansion;

import appeng.api.AECapabilities;
import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.MEStorage;
import com.yongaishide.chaosworld.ae2.ChaosGenericInventory;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "cool.furry.mc.neoforge.projectexpansion.block.entity.BlockEntityTransmutationInterface", remap = false)
public class TransmutationInterfaceMEStorageMixin {

    @Inject(method = "registerCapabilities", at = @At("TAIL"), remap = false)
    private static void chaosworld_registerMEStorage(RegisterCapabilitiesEvent event, CallbackInfo ci) {
        var type = cool.furry.mc.neoforge.projectexpansion.registries.BlockEntityTypes.TRANSMUTATION_INTERFACE.get();
        event.registerBlockEntity(AECapabilities.GENERIC_INTERNAL_INV, type,
                (be, v) -> be != null ? new ChaosGenericInventory(be) : null);
        event.registerBlockEntity(AECapabilities.ME_STORAGE, type,
                (be, dir) -> be != null ? new TransmutationMEStorage(be) : null);
    }

    private static class TransmutationMEStorage implements MEStorage {
        private final ChaosGenericInventory inv;
        TransmutationMEStorage(cool.furry.mc.neoforge.projectexpansion.block.entity.BlockEntityTransmutationInterface be) {
            this.inv = new ChaosGenericInventory(be);
        }
        @Override public long insert(AEKey key, long amount, Actionable mode, IActionSource source) { return 0; }
        @Override public long extract(AEKey key, long amount, Actionable mode, IActionSource source) {
            if (amount <= 0) return 0;
            for (int i = 0; i < inv.size(); i++) {
                AEKey k = inv.getKey(i);
                if (k != null && k.equals(key)) return inv.extract(i, key, amount, mode);
            }
            return 0;
        }
        @Override public void getAvailableStacks(KeyCounter out) {
            for (int i = 0; i < inv.size(); i++) {
                AEKey k = inv.getKey(i);
                if (k != null) { long a = inv.getAmount(i); if (a > 0) out.add(k, a); }
            }
        }
        @Override public Component getDescription() { return Component.literal("Transmutation Interface"); }
    }
}
