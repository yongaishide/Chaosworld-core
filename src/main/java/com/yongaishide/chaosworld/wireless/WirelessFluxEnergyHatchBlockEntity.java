package com.yongaishide.chaosworld.wireless;

import aztech.modern_industrialization.MICapabilities;
import aztech.modern_industrialization.api.energy.CableTier;
import aztech.modern_industrialization.api.energy.EnergyApi;
import aztech.modern_industrialization.api.energy.MIEnergyStorage;
import aztech.modern_industrialization.api.machine.holder.EnergyComponentHolder;
import aztech.modern_industrialization.inventory.MIInventory;
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.helper.EnergyHelper;
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity;
import aztech.modern_industrialization.machines.multiblocks.HatchType;
import aztech.modern_industrialization.machines.multiblocks.HatchTypes;
import aztech.modern_industrialization.util.Simulation;
import com.circulation.more_flux_storage.api.IFluxProxyHost;
import com.circulation.more_flux_storage.util.ProxyFluxDevice;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import sonar.fluxnetworks.api.FluxConstants;
import sonar.fluxnetworks.common.connection.FluxNetwork;
import sonar.fluxnetworks.common.connection.ServerFluxNetwork;
import sonar.fluxnetworks.common.device.FluxStorageHandler;
import sonar.fluxnetworks.common.device.TileFluxDevice;

public class WirelessFluxEnergyHatchBlockEntity extends HatchBlockEntity
    implements EnergyComponentHolder, IFluxProxyHost, ProxyFluxDevice.Host {

    private static final long FE_PER_EU = 10;
    public static final long FE_CAPACITY = 50_000_000;

    private final boolean input;

    private final FluxAwareEnergyComponent energy;
    private final MIEnergyStorage miInsertable;
    private final MIEnergyStorage miExtractable;

    private final WirelessFluxTransferHandler transferHandler;
    private ProxyFluxDevice fluxProxyDevice;
    private CompoundTag pendingFluxTag;
    private byte pendingFluxTagType;

    public WirelessFluxEnergyHatchBlockEntity(BEP bep, MachineGuiParameters guiParams, boolean input) {
        super(bep, guiParams, input
            ? OrientationComponent.Params.noFacingNoOutput()
            : OrientationComponent.Params.noFacing(false, false));
        this.input = input;
        this.energy = new FluxAwareEnergyComponent();
        this.miInsertable = energy.buildInsertable(tier -> true);
        this.miExtractable = energy.buildExtractable(tier -> true);
        this.transferHandler = new WirelessFluxTransferHandler(this);

        this.registerComponents(energy, new FluxDataComponent());
    }

    public boolean isInput() {
        return input;
    }

    public long getFeCapacity() {
        return Long.MAX_VALUE;
    }

    public void markEnergyChanged() {
        setChanged();
    }

    @Override
    public HatchType getHatchType() {
        return input ? HatchTypes.ENERGY_INPUT : HatchTypes.ENERGY_OUTPUT;
    }

    @Override
    public boolean upgradesToSteel() {
        return true;
    }

    @Override
    public MIInventory getInventory() {
        return MIInventory.EMPTY;
    }

    @Override
    public EnergyComponent getEnergyComponent() {
        return energy;
    }

    @Override
    public void appendEnergyInputs(List<EnergyComponent> list) {
        if (input) list.add(energy);
    }

    @Override
    public void appendEnergyOutputs(List<EnergyComponent> list) {
        if (!input) list.add(energy);
    }

    @Override
    protected void tickTransfer() {
        if (!input) {
            EnergyHelper.autoOutput(this, orientation, CableTier.HV, miExtractable);
        }
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        ProxyFluxDevice device = getOrCreateFluxProxyDevice();
        device.hostServerTick();
        super.tick();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        getOrCreateFluxProxyDevice().writeCustomTag(tag, FluxConstants.NBT_TILE_UPDATE);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        getOrCreateFluxProxyDevice().syncLevel();
        getOrCreateFluxProxyDevice().readCustomTag(tag, FluxConstants.NBT_TILE_UPDATE);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onChunkUnloaded() {
        getOrCreateFluxProxyDevice().hostChunkUnloaded();
    }

    @Override
    public void setRemoved() {
        getOrCreateFluxProxyDevice().hostRemoved();
    }

    @Override
    public ProxyFluxDevice getFluxProxyDevice() {
        ProxyFluxDevice device = getOrCreateFluxProxyDevice();
        if (level != null) {
            device.syncLevel();
        }
        return device;
    }

    @Override
    public int getFluxNetworkId() {
        return getOrCreateFluxProxyDevice().getNetworkID();
    }

    @Override
    public void setFluxOwner(UUID uuid) {
        getOrCreateFluxProxyDevice().setOwnerUUID(uuid);
        syncFluxData();
    }

    @Override
    public boolean canOpenFluxGui(Player player) {
        return getOrCreateFluxProxyDevice().canOpenGui(player);
    }

    @Override
    public void writeFluxTag(CompoundTag tag, byte type) {
        getOrCreateFluxProxyDevice().writeCustomTag(tag, type);
    }

    @Override
    public void readFluxTag(CompoundTag tag, byte type) {
        ProxyFluxDevice device = getOrCreateFluxProxyDevice();
        device.syncLevel();
        if (device.getLevel() == null) {
            pendingFluxTag = tag.copy();
            pendingFluxTagType = type;
        } else {
            device.readCustomTag(tag, type);
        }
    }

    @Override
    public @NotNull BlockEntity getTE() {
        return this;
    }

    @Override
    public @NotNull FluxStorageHandler getProxyTransferHandler() {
        return transferHandler;
    }

    @Override
    public @NotNull Component getProxyDisplayName() {
        return Component.translatable(input
            ? "block.chaosworld_core.wireless_flux_energy_input_hatch"
            : "block.chaosworld_core.wireless_flux_energy_output_hatch");
    }

    @Override
    public @NotNull ItemStack getProxyDisplayStack() {
        return new ItemStack(input
            ? ModWirelessContent.WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK_ITEM.get()
            : ModWirelessContent.WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK_ITEM.get());
    }

    private ProxyFluxDevice getOrCreateFluxProxyDevice() {
        if (fluxProxyDevice == null) {
            fluxProxyDevice = new ProxyFluxDevice(this, getType(), worldPosition, getBlockState());
        }
        if (level != null) {
            fluxProxyDevice.syncLevel();
            fluxProxyDevice.setLevel(level);
        }
        return fluxProxyDevice;
    }

    private void syncFluxData() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 11);
        }
    }

    public static void registerEnergyApi(BlockEntityType<?> bet) {
        MICapabilities.onEvent(event -> {
            event.registerBlockEntity(EnergyApi.SIDED, bet, (be, direction) -> {
                WirelessFluxEnergyHatchBlockEntity hatch = (WirelessFluxEnergyHatchBlockEntity) be;
                if (hatch.input) {
                    return hatch.miInsertable;
                } else {
                    if (hatch.orientation.outputDirection == direction) {
                        return hatch.miExtractable;
                    } else {
                        return null;
                    }
                }
            });
        });
    }

    // EnergyComponent directly backed by Flux handler's mBuffer as the single source of truth.
    private class FluxAwareEnergyComponent extends EnergyComponent {
        FluxAwareEnergyComponent() {
            super(WirelessFluxEnergyHatchBlockEntity.this, Long.MAX_VALUE / FE_PER_EU);
        }

        @Override
        public long getEu() {
            if (fluxProxyDevice == null) return 0;
            if (!(fluxProxyDevice.getNetwork() instanceof ServerFluxNetwork sfn)) return 0;
            long totalFe = 0;
            for (TileFluxDevice pl : sfn.getLogicalDevices(FluxNetwork.PLUG)) {
                if (pl == fluxProxyDevice) continue;
                totalFe += pl.getTransferHandler().getBuffer();
            }
            return totalFe / FE_PER_EU;
        }

        @Override
        public long insertEu(long max, Simulation simulation) {
            if (input) return 0;
            if (fluxProxyDevice == null) return 0;
            if (!(fluxProxyDevice.getNetwork() instanceof ServerFluxNetwork sfn)) return 0;
            long maxFe = max * FE_PER_EU;
            long totalFe = 0;
            if (simulation.isSimulating()) {
                for (TileFluxDevice pt : sfn.getLogicalDevices(FluxNetwork.POINT)) {
                    if (pt == fluxProxyDevice) continue;
                    totalFe += pt.getTransferHandler().getRequest();
                }
                return Math.min(max, totalFe / FE_PER_EU);
            }
            long remaining = maxFe;
            long sent = 0;
            for (TileFluxDevice pt : sfn.getLogicalDevices(FluxNetwork.POINT)) {
                if (pt == fluxProxyDevice) continue;
                if (pt.getTransferHandler() instanceof FluxStorageHandler h) {
                    long req = h.getRequest();
                    long toSend = Math.min(req, remaining);
                    if (toSend > 0) {
                        h.addToBuffer(toSend);
                        remaining -= toSend;
                        sent += toSend;
                    }
                }
                if (remaining <= 0) break;
            }
            if (sent > 0) setChanged();
            return sent / FE_PER_EU;
        }

        @Override
        public long consumeEu(long max, Simulation simulation) {
            if (!input) return 0;
            if (fluxProxyDevice == null) return 0;
            if (!(fluxProxyDevice.getNetwork() instanceof ServerFluxNetwork sfn)) return 0;
            if (simulation.isSimulating()) {
                return Math.min(max, getEu());
            }
            long remainingFe = max * FE_PER_EU;
            long pulledFe = 0;
            for (TileFluxDevice pl : sfn.getLogicalDevices(FluxNetwork.PLUG)) {
                if (pl == fluxProxyDevice) continue;
                if (pl.getTransferHandler() instanceof FluxStorageHandler h) {
                    long buf = h.getBuffer();
                    long toPull = Math.min(buf, remainingFe - pulledFe);
                    if (toPull > 0) {
                        h.removeFromBuffer(toPull);
                        pulledFe += toPull;
                    }
                }
                if (pulledFe >= remainingFe) break;
            }
            if (pulledFe > 0) setChanged();
            return pulledFe / FE_PER_EU;
        }

        @Override
        public long getCapacity() {
            return Long.MAX_VALUE / FE_PER_EU;
        }

        @Override
        public void writeNbt(CompoundTag tag, HolderLookup.Provider registries) {
        }

        @Override
        public void readNbt(CompoundTag tag, HolderLookup.Provider registries, boolean isUpgradingMachine) {
        }
    }

    private class FluxDataComponent implements MachineComponent.ServerOnly {
        private static final String KEY_NETWORK_ID = "fluxNetworkId";
        private static final String KEY_OWNER_UUID = "fluxOwnerUuid";

        @Override
        public void writeNbt(CompoundTag tag, HolderLookup.Provider registries) {
            ProxyFluxDevice device = getOrCreateFluxProxyDevice();
            tag.putInt(KEY_NETWORK_ID, device.getNetworkID());
            tag.putUUID(KEY_OWNER_UUID, device.getOwnerUUID());
            device.writeCustomTag(tag, FluxConstants.NBT_SAVE_ALL);
        }

        @Override
        public void readNbt(CompoundTag tag, HolderLookup.Provider registries, boolean isUpgradingMachine) {
            ProxyFluxDevice device = getOrCreateFluxProxyDevice();
            device.syncLevel();
            device.readCustomTag(tag, FluxConstants.NBT_SAVE_ALL);
        }
    }
}
