package com.yongaishide.chaosworld.ae2;

import appeng.api.behaviors.GenericInternalInventory;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.config.Actionable;
import cool.furry.mc.neoforge.projectexpansion.block.entity.BlockEntityTransmutationInterface;
import cool.furry.mc.neoforge.projectexpansion.util.Util;
import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import moze_intel.projecte.api.proxy.IEMCProxy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Method;
import java.math.BigInteger;

public class ChaosGenericInventory implements GenericInternalInventory {

    private final BlockEntityTransmutationInterface be;
    private static Method _fetchKnowledgeMethod;

    public ChaosGenericInventory(BlockEntityTransmutationInterface be) {
        this.be = be;
    }

    private ItemInfo[] fetchKnowledge() {
        try {
            if (_fetchKnowledgeMethod == null) {
                _fetchKnowledgeMethod = BlockEntityTransmutationInterface.class.getDeclaredMethod("fetchKnowledge");
                _fetchKnowledgeMethod.setAccessible(true);
            }
            return (ItemInfo[]) _fetchKnowledgeMethod.invoke(be);
        } catch (Exception e) {
            return new ItemInfo[0];
        }
    }

    private BigInteger getEmc() {
        if (be.owner == null) return BigInteger.ZERO;
        IKnowledgeProvider provider = Util.getKnowledgeProvider(be.owner);
        if (provider == null) return BigInteger.ZERO;
        return provider.getEmc();
    }

    @Override
    public int size() {
        return fetchKnowledge().length;
    }

    @Override
    public GenericStack getStack(int slot) {
        AEKey key = getKey(slot);
        if (key == null) return null;
        return new GenericStack(key, getAmount(slot));
    }

    @Override
    public AEKey getKey(int slot) {
        ItemInfo[] knowledge = fetchKnowledge();
        if (slot < 0 || slot >= knowledge.length) return null;
        ItemStack stack = knowledge[slot].createStack();
        if (stack.isEmpty()) return null;
        return AEItemKey.of(stack);
    }

    @Override
    public long getAmount(int slot) {
        ItemInfo[] knowledge = fetchKnowledge();
        if (slot < 0 || slot >= knowledge.length) return 0;
        long itemEmc = IEMCProxy.INSTANCE.getValue(knowledge[slot]);
        if (itemEmc <= 0) return 0;
        BigInteger emc = getEmc();
        BigInteger available = emc.divide(BigInteger.valueOf(itemEmc));
        return available.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? Long.MAX_VALUE : available.longValue();
    }

    @Override
    public long getMaxAmount(AEKey key) {
        return Long.MAX_VALUE;
    }

    @Override
    public long getCapacity(AEKeyType keyType) {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean canInsert() {
        return false;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public void setStack(int slot, GenericStack stack) {
    }

    @Override
    public boolean isSupportedType(AEKeyType type) {
        return type == AEKeyType.items();
    }

    @Override
    public boolean isAllowedIn(int slot, AEKey key) {
        return false;
    }

    @Override
    public long insert(int slot, AEKey key, long amount, Actionable mode) {
        return 0;
    }

    @Override
    public long extract(int slot, AEKey key, long amount, Actionable mode) {
        if (!(key instanceof AEItemKey itemKey)) return 0;
        ItemInfo[] knowledge = fetchKnowledge();
        if (slot >= knowledge.length) return 0;

        long itemEmc = IEMCProxy.INSTANCE.getValue(knowledge[slot]);
        if (itemEmc <= 0) return 0;

        BigInteger emc = getEmc();
        BigInteger maxAvailable = emc.divide(BigInteger.valueOf(itemEmc));
        long available = maxAvailable.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? Long.MAX_VALUE : maxAvailable.longValue();
        long toExtract = Math.min(amount, available);
        if (toExtract <= 0) return 0;

        if (mode == Actionable.SIMULATE) return toExtract;

        BigInteger cost = BigInteger.valueOf(itemEmc).multiply(BigInteger.valueOf(toExtract));
        IKnowledgeProvider provider = Util.getKnowledgeProvider(be.owner);
        if (provider == null) return 0;
        provider.setEmc(provider.getEmc().subtract(cost));
        ServerPlayer player = Util.getPlayer(be.getLevel(), be.owner);
        if (player != null) provider.syncEmc(player);

        return toExtract;
    }

    @Override
    public void beginBatch() {}

    @Override
    public void endBatch() {}

    @Override
    public void endBatchSuppressed() {}

    @Override
    public void onChange() {}
}
