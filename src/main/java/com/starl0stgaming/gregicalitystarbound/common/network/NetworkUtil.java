package com.starl0stgaming.gregicalitystarbound.common.network;

import gregtech.api.GregTechAPI;

public class NetworkUtil {
    public static void registerPackets() {
        GregTechAPI.networkHandler.registerPacket(PacketEntity.class);
    }

}
