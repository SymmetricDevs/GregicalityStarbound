package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketData;

public class TelemetryPacket<T extends TelemetryPacketData> {

    private int priority;
    //gets cleared after each send operation is completed, if value equals 0 then it will be sent to whole connection;
    private int destinationID;
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

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }
}
