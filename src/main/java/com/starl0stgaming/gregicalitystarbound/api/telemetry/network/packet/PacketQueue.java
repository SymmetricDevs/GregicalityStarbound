package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketData;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.List;

public class PacketQueue {

    public List<TelemetryPacket> telemetryPackets;

    public PacketQueue() {

    }

    public void addPacketToQueue(TelemetryPacket packet) {
        this.telemetryPackets.add(packet);
    }

    public void removePacketFromQueue(TelemetryPacket packet) {
        this.telemetryPackets.remove(packet);
    }

    public List<TelemetryPacket> getTelemetryPackets() {
        return telemetryPackets;
    }
}
