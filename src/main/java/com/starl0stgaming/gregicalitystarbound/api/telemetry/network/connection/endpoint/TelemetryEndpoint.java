package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import net.minecraft.nbt.NBTTagCompound;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.encryption.AuthKey;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetworkManager;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;

public class TelemetryEndpoint {

    public ArrayList<TelemetryPacketPayload> dataBuffer;
    protected String packetDiscriminator;
    protected AuthKey authKey;
    // Ids are set in constructor, but it might be better to set them in TelemetryConnection
    private int id;
    private TelemetryConnection connection;
    private PriorityQueue<TelemetryPacket> inPacketQueue;
    private PriorityQueue<TelemetryPacket> outPacketQueue;
    private boolean enableDiscriminator;
    private boolean enableEncryption;

    public TelemetryEndpoint(int id) {
        this.connection = null;
        this.id = id;
        this.dataBuffer = new ArrayList<>();

        this.enableDiscriminator = false;
        this.enableEncryption = false;

        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));

        this.packetDiscriminator = "";
        this.authKey = new AuthKey(id, packetDiscriminator);
    }

    public TelemetryEndpoint(int id, String discriminator, AuthKey authKey) {
        this.connection = null;
        this.id = id;
        this.dataBuffer = new ArrayList<>();
        // TODO: LOOK AT THIS SHIT BC IT DOESNT FIT, HOW DO I EVEN GET THE DISCRIMINATOR HUH
        this.enableDiscriminator = false;
        this.enableEncryption = false;

        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));

        this.packetDiscriminator = discriminator;
        this.authKey = authKey;
    }

    public TelemetryEndpoint(NBTTagCompound compoundTagAt) {
        deserializeNBT(compoundTagAt);
    }

    public void update() {
        // reads packet queues and processes in packets and sends out packets

        // read in packet queue
        if (!this.inPacketQueue.isEmpty()) {

            for (int i = 0; i < this.inPacketQueue.toArray().length; i++) {
                TelemetryPacket packetIn = this.inPacketQueue.poll();
                if (!packetIn.getDiscriminator().equals(this.packetDiscriminator) && this.enableDiscriminator) break;

                TelemetryPacketPayload packetPayload = packetIn.getPacketPayload();

                this.dataBuffer.add(packetPayload);
            }
        }

        // read out packet queue and send packets to specified destination in packet info or whole network
        if (!this.outPacketQueue.isEmpty()) {
            for (int i = 0; i < this.outPacketQueue.toArray().length; i++) {
                this.connection.sendPacketToDestination(this.outPacketQueue.poll());
            }
        }
    }

    /**
     * @return returns the TelemetryPacketPayload in the index 0 of the endpoint's data buffer.
     */
    public TelemetryPacketPayload poll() {
        return this.dataBuffer.remove(0);
    }

    public void receivePacket(TelemetryPacket packet) {
        this.inPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Received packet on endpoint with id " + id);
    }

    public void sendPacket(TelemetryPacket packet) {
        if (this.connection == null) {
            GCSBLog.LOGGER.error("[ERROR] Telemetry Endpoint with id " + this.getId() +
                    " cant send packet because it has no bound network!");
            return;
        }
        this.outPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Sent packet from endpoint with id " + id);
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("id", this.id);
        nbt.setString("discr", packetDiscriminator);
        return nbt;
    }

    public TelemetryConnection getNetwork() {
        return connection;
    }

    public void setNetwork(TelemetryConnection network) {
        this.connection = network;
    }

    public void deserializeNBT(NBTTagCompound data) {
        this.id = data.getInteger("id");
        if (this.id > TelemetryNetworkManager.ENDPOINT_ID_COUNT) {
            TelemetryNetworkManager.ENDPOINT_ID_COUNT = this.id;
        }
        this.packetDiscriminator = data.getString("discr");
    }

    public void setConnection(TelemetryConnection connection) {
        this.connection = connection;
    }

    public int getId() {
        return id;
    }

    public String getPacketDiscriminator() {
        return packetDiscriminator;
    }

    public void setPacketDiscriminator(String packetDiscriminator) {
        this.packetDiscriminator = packetDiscriminator;
    }

    public AuthKey getAuthKey() {
        return authKey;
    }

    public void setAuthKey(AuthKey authKey) {
        this.authKey = authKey;
    }
}
