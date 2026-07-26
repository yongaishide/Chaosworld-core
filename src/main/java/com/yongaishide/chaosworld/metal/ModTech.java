package com.yongaishide.chaosworld.metal;

import com.yongaishide.chaosworld.ChaosWorld;
import com.yongaishide.chaosworld.item.BaseItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModTech {
    public static final Map<String, DeferredHolder<Item, ? extends Item>> TECH_ITEMS = new LinkedHashMap<>();
    public static final Map<String, DeferredHolder<Block, ? extends Block>> TECH_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, DeferredHolder<Item, ? extends Item>> TECH_BLOCK_ITEMS = new LinkedHashMap<>();

    public static final String[][] TECHS = {
        {"tech_1", "\u4E00\u9636\u79D1\u6280"},
        {"tech_2", "\u4E8C\u9636\u79D1\u6280"},
        {"tech_3", "\u4E09\u9636\u79D1\u6280"},
        {"tech_4", "\u56DB\u9636\u79D1\u6280"},
        {"tech_5", "\u4E94\u9636\u79D1\u6280"},
        {"tech_6", "\u516D\u9636\u79D1\u6280"},
        {"tech_7", "\u4E03\u9636\u79D1\u6280"},
        {"tech_8", "\u516B\u9636\u79D1\u6280"},
        {"tech_9", "\u4E5D\u9636\u79D1\u6280"},
    };

    public static final String[][] TYPES = {
        {"ingot", "\u952D"},
        {"nugget", "\u7C92"},
        {"plate", "\u677F"},
        {"dust", "\u7C89"},
        {"gear", "\u9F7F\u8F6E"},
        {"rod", "\u68D2"},
    };

    public static final Map<String, Integer> TECH_COLORS = new HashMap<>();
    static {
        TECH_COLORS.put("tech_1", 0xFFe07802);
        TECH_COLORS.put("tech_2", 0xFF6c4646);
        TECH_COLORS.put("tech_3", 0xFF032F34);
        TECH_COLORS.put("tech_4", 0xFF03DE6D);
        TECH_COLORS.put("tech_5", 0xFFf5f5f5);
        TECH_COLORS.put("tech_6", 0xFFf5f5f5);
        TECH_COLORS.put("tech_7", 0xFFf5f5f5);
        TECH_COLORS.put("tech_8", 0xFFf5f5f5);
        TECH_COLORS.put("tech_9", 0xFFf5f5f5);
    }

    public static int getColorForItem(String path) {
        if (path.endsWith("_block")) {
            String tech = path.substring(0, path.length() - 6);
            return TECH_COLORS.getOrDefault(tech, 0xFFFFFFFF);
        }
        for (String[] tech : TECHS) {
            if (path.endsWith("_" + tech[0])) {
                return TECH_COLORS.getOrDefault(tech[0], 0xFFFFFFFF);
            }
        }
        return 0xFFFFFFFF;
    }

    public static void register() {
        for (String[] tech : TECHS) {
            String tname = tech[0];
            for (String[] type : TYPES) {
                String suffix = type[0];
                String itemId = suffix + "_" + tname;
                DeferredHolder<Item, ? extends Item> item = ChaosWorld.ITEMS.register(itemId,
                    () -> new BaseItem(new Item.Properties(), false));
                TECH_ITEMS.put(itemId, item);
            }

            String blockId = tname + "_block";
            DeferredHolder<Block, ? extends Block> block = ChaosWorld.BLOCKS.register(blockId,
                () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.METAL)));
            TECH_BLOCKS.put(blockId, block);

            DeferredHolder<Item, ? extends Item> blockItem = ChaosWorld.ITEMS.register(blockId,
                () -> new BlockItem(block.get(), new Item.Properties()));
            TECH_BLOCK_ITEMS.put(blockId, blockItem);
        }
    }
}
