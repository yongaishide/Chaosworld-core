package com.yongaishide.chaosworld;

import com.yongaishide.chaosworld.item.BaseItem;
import com.yongaishide.chaosworld.metal.ModMetals;
import com.yongaishide.chaosworld.metal.ModTech;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ChaosWorld.MODID)
public class ChaosWorld {
    public static final String MODID = "chaosworld-core";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Manual items
    public static final DeferredHolder<Item, BaseItem> CRYPTID_CORE = ITEMS.register("cryptid_core", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> STARLIGHT_GEMSTONE = ITEMS.register("starlight_gemstone", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> HUIXING_GEMSTONE = ITEMS.register("huixing_gemstone", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> NATURE_GEMSTONE = ITEMS.register("nature_gemstone", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> SPARKLING_GEMSTONES = ITEMS.register("sparkling_gemstones", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> STARS_GEMSTONE = ITEMS.register("stars_gemstone", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> SUN_GEMSTONE = ITEMS.register("sun_gemstone", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_1 = ITEMS.register("crystal_ore_1", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_2 = ITEMS.register("crystal_ore_2", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_3 = ITEMS.register("crystal_ore_3", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_4 = ITEMS.register("crystal_ore_4", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_5 = ITEMS.register("crystal_ore_5", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_6 = ITEMS.register("crystal_ore_6", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_7 = ITEMS.register("crystal_ore_7", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ORE_8 = ITEMS.register("crystal_ore_8", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> BEILUNWUZHIQIU = ITEMS.register("beilunwuzhiqiu", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> DRAGON_CATALYST = ITEMS.register("dragon_catalyst", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> INFINITE_RUNES = ITEMS.register("infinite_runes", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> RUNES_1 = ITEMS.register("runes_1", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> ATMIUM_INGOT = ITEMS.register("atmium_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> IRON_GOLEM_CORE = ITEMS.register("iron_golem_core", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> AQUAMARINE = ITEMS.register("aquamarine", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WETWARE_ASSEMBLY = ITEMS.register("wetware_assembly", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WETWARE_COMPUTER = ITEMS.register("wetware_computer", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WETWARE_MAINFRAME = ITEMS.register("wetware_mainframe", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WETWARE_PROCESSOR = ITEMS.register("wetware_processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> COLORFUL_CORE = ITEMS.register("colorful_core", () -> new BaseItem(new Item.Properties(), true));
    public static final DeferredHolder<Item, BaseItem> COLORFUL_ENERGY_CORE = ITEMS.register("colorful_energy_core", () -> new BaseItem(new Item.Properties(), true));
    public static final DeferredHolder<Item, BaseItem> TERMINAL_PASS = ITEMS.register("terminal_pass", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> TWILIGHT_CATALYST = ITEMS.register("twilight_catalyst", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> MANA_CRYSTAL1 = ITEMS.register("mana_crystal1", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> MANA_CRYSTAL2 = ITEMS.register("mana_crystal2", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> MANA_CRYSTAL3 = ITEMS.register("mana_crystal3", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> FORGEPLATE = ITEMS.register("forgeplate", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> FURANCE1 = ITEMS.register("furance1", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> FURANCE2 = ITEMS.register("furance2", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> FURANCE3 = ITEMS.register("furance3", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_ASSEMBLY = ITEMS.register("crystal_assembly", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_COMPUTER = ITEMS.register("crystal_computer", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_MAINFRAME = ITEMS.register("crystal_mainframe", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_PROCESSOR = ITEMS.register("crystal_processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CHAOTIC_METAL_INGOT = ITEMS.register("chaotic_metal_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> DRACONIC_METAL_INGOT = ITEMS.register("draconic_metal_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WYVERN_METAL_INGOT = ITEMS.register("wyvern_metal_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CHAOTIC_METAL_DUST = ITEMS.register("chaotic_metal_dust", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> DRACONIC_METAL_DUST = ITEMS.register("draconic_metal_dust", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WYVERN_METAL_DUST = ITEMS.register("wyvern_metal_dust", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CHAOTIC_METAL_GEAR = ITEMS.register("chaotic_metal_gear", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> DRACONIC_METAL_GEAR = ITEMS.register("draconic_metal_gear", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WYVERN_METAL_GEAR = ITEMS.register("wyvern_metal_gear", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CHAOTIC_METAL_NUGGET = ITEMS.register("chaotic_metal_nugget", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> DRACONIC_METAL_NUGGET = ITEMS.register("draconic_metal_nugget", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WYVERN_METAL_NUGGET = ITEMS.register("wyvern_metal_nugget", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> QUANTUM_ASSEMBLY = ITEMS.register("quantum_assembly", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> QUANTUM_COMPUTER = ITEMS.register("quantum_computer", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> QUANTUM_MAINFRAME = ITEMS.register("quantum_mainframe", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> QUANTUM_PROCESSOR = ITEMS.register("quantum_processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> NANO_ASSEMBLY = ITEMS.register("nano_assembly", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> NANO_COMPUTER = ITEMS.register("nano_computer", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> NANO_MAINFRAME = ITEMS.register("nano_mainframe", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> NANO_PROCESSOR = ITEMS.register("nano_processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CENTRAL_PROCESSING = ITEMS.register("central_processing", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CHARGINGMAGICEMERALDCRYSTAL = ITEMS.register("chargingmagicemeraldcrystal", () -> new BaseItem(new Item.Properties(), true));
    public static final DeferredHolder<Item, BaseItem> CIRCUIT_PROCESSOR = ITEMS.register("circuit_processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL = ITEMS.register("crystal", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> CRYSTAL_CHIP = ITEMS.register("crystal_chip", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> ZELUOSISHUIJING = ITEMS.register("zeluosishuijing", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> REDHEJIN = ITEMS.register("redhejin", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> REDKONGZHIDIANLU = ITEMS.register("redkongzhidianlu", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> STAINLESS_STEEL_INGOT = ITEMS.register("stainless_steel_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> WORKSTATION = ITEMS.register("workstation", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> MICROPROCESSOR = ITEMS.register("microprocessor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> INTEGRATED = ITEMS.register("integrated", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> PROCESSOR = ITEMS.register("processor", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> KYRONITE = ITEMS.register("kyronite", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> LAIZEERSHUIJING = ITEMS.register("laizeershuijing", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> QUANTUM_INGOT = ITEMS.register("quantum_ingot", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> BASIC_INTEGRATED = ITEMS.register("basic_integrated", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> ADVANCED_INTEGRATED = ITEMS.register("advanced_integrated", () -> new BaseItem(new Item.Properties(), false));
    public static final DeferredHolder<Item, BaseItem> MAGIC_EMERALD_CRYSTAL = ITEMS.register("magicemeraldcrystal", () -> new BaseItem(new Item.Properties(), false));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHAOSWORLD_TAB = CREATIVE_MODE_TABS.register("chaosworld", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chaosworld"))
            .icon(() -> new ItemStack(CRYPTID_CORE.get()))
            .displayItems((parameters, output) -> {
                output.accept(CRYPTID_CORE.get());
                output.accept(STARLIGHT_GEMSTONE.get());
                output.accept(HUIXING_GEMSTONE.get());
                output.accept(NATURE_GEMSTONE.get());
                output.accept(SPARKLING_GEMSTONES.get());
                output.accept(STARS_GEMSTONE.get());
                output.accept(SUN_GEMSTONE.get());
                output.accept(CRYSTAL_ORE_1.get());
                output.accept(CRYSTAL_ORE_2.get());
                output.accept(CRYSTAL_ORE_3.get());
                output.accept(CRYSTAL_ORE_4.get());
                output.accept(CRYSTAL_ORE_5.get());
                output.accept(CRYSTAL_ORE_6.get());
                output.accept(CRYSTAL_ORE_7.get());
                output.accept(CRYSTAL_ORE_8.get());
                output.accept(BEILUNWUZHIQIU.get());
                output.accept(DRAGON_CATALYST.get());
                output.accept(INFINITE_RUNES.get());
                output.accept(RUNES_1.get());
                output.accept(ATMIUM_INGOT.get());
                output.accept(IRON_GOLEM_CORE.get());
                output.accept(AQUAMARINE.get());
                output.accept(WETWARE_ASSEMBLY.get());
                output.accept(WETWARE_COMPUTER.get());
                output.accept(WETWARE_MAINFRAME.get());
                output.accept(WETWARE_PROCESSOR.get());
                output.accept(COLORFUL_CORE.get());
                output.accept(COLORFUL_ENERGY_CORE.get());
                output.accept(TERMINAL_PASS.get());
                output.accept(TWILIGHT_CATALYST.get());
                output.accept(MANA_CRYSTAL1.get());
                output.accept(MANA_CRYSTAL2.get());
                output.accept(MANA_CRYSTAL3.get());
                output.accept(FORGEPLATE.get());
                output.accept(FURANCE1.get());
                output.accept(FURANCE2.get());
                output.accept(FURANCE3.get());
                output.accept(CRYSTAL_ASSEMBLY.get());
                output.accept(CRYSTAL_COMPUTER.get());
                output.accept(CRYSTAL_MAINFRAME.get());
                output.accept(CRYSTAL_PROCESSOR.get());
                output.accept(CHAOTIC_METAL_INGOT.get());
                output.accept(DRACONIC_METAL_INGOT.get());
                output.accept(WYVERN_METAL_INGOT.get());
                output.accept(CHAOTIC_METAL_DUST.get());
                output.accept(DRACONIC_METAL_DUST.get());
                output.accept(WYVERN_METAL_DUST.get());
                output.accept(CHAOTIC_METAL_GEAR.get());
                output.accept(DRACONIC_METAL_GEAR.get());
                output.accept(WYVERN_METAL_GEAR.get());
                output.accept(CHAOTIC_METAL_NUGGET.get());
                output.accept(DRACONIC_METAL_NUGGET.get());
                output.accept(WYVERN_METAL_NUGGET.get());
                output.accept(QUANTUM_ASSEMBLY.get());
                output.accept(QUANTUM_COMPUTER.get());
                output.accept(QUANTUM_MAINFRAME.get());
                output.accept(QUANTUM_PROCESSOR.get());
                output.accept(NANO_ASSEMBLY.get());
                output.accept(NANO_COMPUTER.get());
                output.accept(NANO_MAINFRAME.get());
                output.accept(NANO_PROCESSOR.get());
                output.accept(CENTRAL_PROCESSING.get());
                output.accept(CHARGINGMAGICEMERALDCRYSTAL.get());
                output.accept(CIRCUIT_PROCESSOR.get());
                output.accept(CRYSTAL.get());
                output.accept(CRYSTAL_CHIP.get());
                output.accept(ZELUOSISHUIJING.get());
                output.accept(REDHEJIN.get());
                output.accept(REDKONGZHIDIANLU.get());
                output.accept(STAINLESS_STEEL_INGOT.get());
                output.accept(WORKSTATION.get());
                output.accept(MICROPROCESSOR.get());
                output.accept(INTEGRATED.get());
                output.accept(PROCESSOR.get());
                output.accept(KYRONITE.get());
                output.accept(LAIZEERSHUIJING.get());
                output.accept(QUANTUM_INGOT.get());
                output.accept(BASIC_INTEGRATED.get());
                output.accept(ADVANCED_INTEGRATED.get());
                output.accept(MAGIC_EMERALD_CRYSTAL.get());
                for (var entry : ModMetals.METAL_ITEMS.values()) {
                    output.accept(entry.get());
                }
                for (var entry : ModMetals.METAL_BLOCK_ITEMS.values()) {
                    output.accept(entry.get());
                }
                for (var entry : ModTech.TECH_ITEMS.values()) {
                    output.accept(entry.get());
                }
                for (var entry : ModTech.TECH_BLOCK_ITEMS.values()) {
                    output.accept(entry.get());
                }
            })
            .build());

    public ChaosWorld(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        ModMetals.register();
        ModTech.register();

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                var window = Minecraft.getInstance().getWindow().getWindow();
                org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback(window, handle -> {
                    org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose(window, false);
                    Minecraft.getInstance().setScreen(new com.yongaishide.chaosworld.client.ConfirmQuitScreen());
                });
            });
        }

        private static int getItemColor(String path) {
            int c = ModMetals.getColorForItem(path);
            if (c != 0xFFFFFFFF) return c;
            return ModTech.getColorForItem(path);
        }

        @SubscribeEvent
        public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
            var items = new java.util.ArrayList<Item>();
            ModMetals.METAL_ITEMS.values().forEach(ro -> items.add(ro.get()));
            ModMetals.METAL_BLOCK_ITEMS.values().forEach(ro -> items.add(ro.get()));
            ModTech.TECH_ITEMS.values().forEach(ro -> items.add(ro.get()));
            ModTech.TECH_BLOCK_ITEMS.values().forEach(ro -> items.add(ro.get()));

            event.register((stack, tintIndex) -> {
                if (tintIndex > 0) return -1;
                String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
                return getItemColor(path);
            }, items.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
            var blocks = new java.util.ArrayList<Block>();
            ModMetals.METAL_BLOCKS.values().forEach(ro -> blocks.add(ro.get()));
            ModTech.TECH_BLOCKS.values().forEach(ro -> blocks.add(ro.get()));

            event.register((state, level, pos, tintIndex) -> {
                if (tintIndex > 0) return -1;
                String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
                return getItemColor(path);
            }, blocks.toArray(new Block[0]));
        }
    }
}
