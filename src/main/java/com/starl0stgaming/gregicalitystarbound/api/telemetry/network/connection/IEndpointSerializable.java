package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

// Allows a class to be linked to a telemetry endpoint
public interface IEndpointSerializable {
    NBTTagCompound writeToNBT(NBTTagCompound ntc);
    void handleMessage(NBTTagCompound nbt);
}
