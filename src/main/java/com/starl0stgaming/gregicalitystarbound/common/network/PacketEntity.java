package com.starl0stgaming.gregicalitystarbound.common.network;

import gregtech.api.network.IPacket;
import gregtech.api.network.IServerExecutor;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketEntity implements IPacket, IServerExecutor {
    private Entity target;
    private int messageId;

    public PacketEntity() {
    }

    public PacketEntity(Entity target, int messageId) {
        this.target = target;
        this.messageId = messageId;
    }

    @Override
    public void encode(PacketBuffer out) {
        out.writeInt(target.world.provider.getDimension());
        out.writeInt(target.getEntityId());
        out.writeInt(messageId);
    }

    @Override
    public void decode(PacketBuffer in) {
        World world;
        world = DimensionManager.getWorld(in.readInt());
        this.target = world.getEntityByID(in.readInt());

        this.messageId = in.readInt();
    }

    @Override
    public void executeServer(NetHandlerPlayServer netHandlerPlayServer) {
        if (target instanceof IReceivingEntity) {
            ((IReceivingEntity) target).receivePacket(messageId);
        }
    }
}
