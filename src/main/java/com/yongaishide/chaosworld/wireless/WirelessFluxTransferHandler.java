package com.yongaishide.chaosworld.wireless;

import com.circulation.more_flux_storage.util.AbstractFluxTransferHandler;

public class WirelessFluxTransferHandler extends AbstractFluxTransferHandler {

    public WirelessFluxTransferHandler(WirelessFluxEnergyHatchBlockEntity hatch) {
    }

    @Override
    public long getRequest() {
        return 0;
    }

    @Override
    public void addToBuffer(long amount) {
    }

    @Override
    public long removeFromBuffer(long amount) {
        return 0;
    }
}
