package com.yongaishide.chaosworld.item;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaseItem extends Item {
    private final boolean hasGlow;

    public BaseItem(Properties properties, boolean hasGlow) {
        super(properties);
        this.hasGlow = hasGlow;
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return hasGlow || super.isFoil(stack);
    }
}
