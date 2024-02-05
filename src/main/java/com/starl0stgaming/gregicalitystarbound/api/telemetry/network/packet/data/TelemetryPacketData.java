package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TelemetryPacketData {


    private NBTTagCompound nbtTagCompound;

    public TelemetryPacketData() {
        this.nbtTagCompound = new NBTTagCompound();
    }

    public void readContents(NBTTagCompound nbtTagCompound) {
        this.nbtTagCompound = nbtTagCompound;
    }

    public abstract NBTTagCompound writeContents(NBTTagCompound nbtTagCompound);
}
