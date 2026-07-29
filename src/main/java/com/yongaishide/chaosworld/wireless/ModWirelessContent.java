package com.yongaishide.chaosworld.wireless;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import com.yongaishide.chaosworld.ChaosWorld;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModWirelessContent {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChaosWorld.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChaosWorld.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ChaosWorld.MODID);

    private static final BlockBehaviour.Properties HATCH_PROPS = BlockBehaviour.Properties.of()
        .strength(5.0f, 6.0f)
        .sound(SoundType.METAL)
        .requiresCorrectToolForDrops();

    public static final DeferredHolder<Block, WirelessFluxEnergyHatchBlock> WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK;
    public static final DeferredHolder<Block, WirelessFluxEnergyHatchBlock> WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK;
    public static final DeferredHolder<Item, BlockItem> WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK_ITEM;
    public static final DeferredHolder<Item, BlockItem> WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK_ITEM;
    public static final Supplier<BlockEntityType<?>> WIRELESS_FLUX_ENERGY_INPUT_HATCH_BET;
    public static final Supplier<BlockEntityType<?>> WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BET;

    static {
        WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK = BLOCKS.register(
            "wireless_flux_energy_input_hatch",
            () -> new WirelessFluxEnergyHatchBlock(HATCH_PROPS, true));

        WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK = BLOCKS.register(
            "wireless_flux_energy_output_hatch",
            () -> new WirelessFluxEnergyHatchBlock(HATCH_PROPS, false));

        WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK_ITEM = ITEMS.register(
            "wireless_flux_energy_input_hatch",
            () -> new BlockItem(WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK.get(), new Item.Properties()));

        WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK_ITEM = ITEMS.register(
            "wireless_flux_energy_output_hatch",
            () -> new BlockItem(WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK.get(), new Item.Properties()));

        WIRELESS_FLUX_ENERGY_INPUT_HATCH_BET = BLOCK_ENTITY_TYPES.register(
            "wireless_flux_energy_input_hatch",
            () -> {
                WirelessFluxEnergyHatchBlock block = WIRELESS_FLUX_ENERGY_INPUT_HATCH_BLOCK.get();
                BlockEntityType<?>[] betRef = new BlockEntityType<?>[1];
                BlockEntityType<?> bet = BlockEntityType.Builder.of(
                    (pos, state) -> createHatchBE(betRef[0], pos, state, true),
                    block
                ).build(null);
                betRef[0] = bet;
                block.setBlockEntityType(bet);
                WirelessFluxEnergyHatchBlockEntity.registerEnergyApi(bet);
                return bet;
            });

        WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BET = BLOCK_ENTITY_TYPES.register(
            "wireless_flux_energy_output_hatch",
            () -> {
                WirelessFluxEnergyHatchBlock block = WIRELESS_FLUX_ENERGY_OUTPUT_HATCH_BLOCK.get();
                BlockEntityType<?>[] betRef = new BlockEntityType<?>[1];
                BlockEntityType<?> bet = BlockEntityType.Builder.of(
                    (pos, state) -> createHatchBE(betRef[0], pos, state, false),
                    block
                ).build(null);
                betRef[0] = bet;
                block.setBlockEntityType(bet);
                WirelessFluxEnergyHatchBlockEntity.registerEnergyApi(bet);
                return bet;
            });
    }

    private static WirelessFluxEnergyHatchBlockEntity createHatchBE(
        BlockEntityType<?> betRef, BlockPos pos, BlockState state, boolean input) {
        BEP bep = new BEP(betRef, pos, state);
        String idStr = "wireless_flux_energy_" + (input ? "input" : "output") + "_hatch";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChaosWorld.MODID, idStr);
        return new WirelessFluxEnergyHatchBlockEntity(bep,
            new MachineGuiParameters.Builder(id, false).build(), input);
    }

    public static void register() {
        BLOCKS.register(ChaosWorld.getModEventBus());
        ITEMS.register(ChaosWorld.getModEventBus());
        BLOCK_ENTITY_TYPES.register(ChaosWorld.getModEventBus());
    }
}
