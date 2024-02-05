package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TelemetryEndpoint {

    private int id;

    private PriorityQueue<TelemetryPacket> inPacketQueue;
    private PriorityQueue<TelemetryPacket> outPacketQueue;

    //private Discriminator discriminator; or
    //private AuthKey authKey;

    public TelemetryEndpoint() {
        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
    }

    public void update() {
        //reads packet queues and processes in packets and sends out packets
    }

    public void receivePacket(TelemetryPacket packet) {
        this.inPacketQueue.add(packet);
    }

    public void sendPacket(TelemetryPacket packet) {
        this.outPacketQueue.add(packet);
    }

    public TelemetryEndpoint(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
