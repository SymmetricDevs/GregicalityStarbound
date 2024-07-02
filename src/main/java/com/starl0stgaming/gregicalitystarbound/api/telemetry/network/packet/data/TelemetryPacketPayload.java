package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data;

import net.minecraft.nbt.NBTTagCompound;

public class TelemetryPacketPayload {


    private NBTTagCompound nbtTagCompound;

    public TelemetryPacketPayload() {
        this.nbtTagCompound = new NBTTagCompound();
    }
    public TelemetryPacketPayload(NBTTagCompound nbtTagCompound) {
        this.nbtTagCompound = nbtTagCompound;
    }

    public NBTTagCompound getPayload() {
        return nbtTagCompound;
    }

    public void setPayload(NBTTagCompound nbtTagCompound) {
        this.nbtTagCompound = nbtTagCompound;
    }
}
