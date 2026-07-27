package com.yongaishide.chaosworld.wireless;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlock;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import com.circulation.more_flux_storage.api.IFluxProxyHost;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sonar.fluxnetworks.api.FluxConstants;

public class WirelessFluxEnergyHatchBlock extends MachineBlock {

    private static final String FLUX_DATA_TAG = "FluxData";

    private final boolean input;
    private BlockEntityType<?> blockEntityType;

    public WirelessFluxEnergyHatchBlock(Properties properties, boolean input) {
        super((pos, state) -> null, properties);
        this.input = input;
    }

    public void setBlockEntityType(BlockEntityType<?> bet) {
        this.blockEntityType = bet;
    }

    public boolean isInput() {
        return input;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    private static CompoundTag readFluxData(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.contains(FLUX_DATA_TAG, Tag.TAG_COMPOUND) ? tag.getCompound(FLUX_DATA_TAG) : null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level,
                                                         @NotNull BlockPos pos, @NotNull Player player,
                                                         @NotNull BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof IFluxProxyHost host)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (host.getFluxNetworkId() < 0) {
            host.setFluxOwner(player.getUUID());
        }
        if (!host.canOpenFluxGui(player)) {
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            host.getFluxProxyDevice().onPlayerInteract(serverPlayer);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
                             @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IFluxProxyHost host) {
            CompoundTag tag = readFluxData(stack);
            if (tag != null) {
                host.readFluxTag(tag.copy(), FluxConstants.NBT_TILE_DROP);
            }
            if (placer instanceof Player player) {
                host.setFluxOwner(player.getUUID());
            }
        }
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>(super.getDrops(state, builder));
        if (drops.isEmpty()) return drops;

        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof IFluxProxyHost host) {
            for (ItemStack stack : drops) {
                writeFluxData(stack, host);
            }
        }
        return drops;
    }

    private void writeFluxData(ItemStack stack, IFluxProxyHost host) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            CompoundTag fluxTag = new CompoundTag();
            host.writeFluxTag(fluxTag, FluxConstants.NBT_TILE_DROP);
            if (fluxTag.isEmpty()) {
                tag.remove(FLUX_DATA_TAG);
            } else {
                tag.put(FLUX_DATA_TAG, fluxTag);
            }
        });
    }

    @Override
    public WirelessFluxEnergyHatchBlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntityType<?> bet = blockEntityType;
        if (bet == null) return null;
        BEP bep = new BEP(bet, pos, state);
        String idStr = "wireless_flux_energy_" + (input ? "input" : "output") + "_hatch";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(com.yongaishide.chaosworld.ChaosWorld.MODID, idStr);
        return new WirelessFluxEnergyHatchBlockEntity(bep,
            new MachineGuiParameters.Builder(id, false).build(), input);
    }
}
