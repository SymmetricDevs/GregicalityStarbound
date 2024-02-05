package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketData;

public class TelemetryPacket<T extends TelemetryPacketData> {

    private int priority;
    private T packetData;

    public TelemetryPacket() {
        this.priority = priority;
    }


    public T getPacketData() {
        return packetData;
    }

    public void setPacketData(T packetData) {
        this.packetData = packetData;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
