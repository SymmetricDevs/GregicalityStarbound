package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;

public class TelemetryPacket {

    private int priority;
    //gets cleared after each send operation is completed, if value equals 0 then it will be sent to whole connection;
    private long destinationID;
    private TelemetryPacketPayload packetData;

    public TelemetryPacket(int priority) {
        this.priority = priority;
    }


    public TelemetryPacketPayload getPacketData() {
        return packetData;
    }

    public void setPacketData(TelemetryPacketPayload packetData) {
        this.packetData = packetData;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(long destinationID) {
        this.destinationID = destinationID;
    }
}
