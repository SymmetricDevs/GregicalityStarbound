package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data;

import net.minecraft.nbt.NBTTagCompound;

public class RocketPacketData extends TelemetryPacketData {


    public RocketPacketData(int priority) {
        super(/*priority*/);
    }

    @Override
    public void readContents(NBTTagCompound nbtTagCompound) {

    }

    @Override
    public NBTTagCompound writeContents(NBTTagCompound nbtTagCompound) {

        return nbtTagCompound;
    }
}
