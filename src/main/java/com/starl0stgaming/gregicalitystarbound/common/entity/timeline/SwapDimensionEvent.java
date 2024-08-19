package com.starl0stgaming.gregicalitystarbound.common.entity.timeline;

import com.starl0stgaming.gregicalitystarbound.api.space.timeline.TimelineTask;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;
import gregtech.api.util.GTTeleporter;
import gregtech.api.util.TeleportHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

import java.util.ArrayList;
import java.util.List;

public class SwapDimensionEvent implements TimelineTask<EntityRocket> {
    private int dimID;
    private boolean goingDown;
    public SwapDimensionEvent(int dimID, boolean goingDown) {
        this.dimID = dimID;
        this.goingDown = goingDown;
    }

    @Override
    public void execute(EntityRocket handle) {
        double targetY = goingDown ? 512 : 128;
        GCSBTeleporter teleporter = new GCSBTeleporter(TeleportHandler.getWorldByDimensionID(dimID),
                handle.posX, targetY, handle.posZ);

        List<Entity> formerPassengers = new ArrayList<>(handle.getPassengers());
        EntityRocket newRocket = teleport(handle, dimID, teleporter, handle.posX, targetY, handle.posZ);
        for (Entity entity : formerPassengers) {
            if (entity instanceof EntityPlayer player) {
                CommonProxy.teleportingPlayers.put(player, newRocket);
            }
        }
    }

    public static EntityRocket teleport(EntityRocket rocket, int dimension, ITeleporter customTeleporter, double teleportToX, double teleportToY, double teleportToZ) {
        EntityRocket newRocket = rocket;
        if (!rocket.world.isRemote && !rocket.isDead) {
            if (rocket.dimension != dimension) {
                 newRocket = (EntityRocket) rocket.changeDimension(dimension, customTeleporter);
            }
        }
        newRocket.setFlightTime(rocket.getFlightTime());
        newRocket.accelerationY = rocket.accelerationY;
        newRocket.realMotionY = rocket.realMotionY;
        newRocket.setPositionAndUpdate(teleportToX, teleportToY, teleportToZ);
        return newRocket;
    }

    public static class GCSBTeleporter extends Teleporter {
        private final WorldServer worldServerInstance;
        private final double x;
        private final double y;
        private final double z;

        public GCSBTeleporter(WorldServer world, double x, double y, double z) {
            super(world);
            this.worldServerInstance = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void placeEntity(World world, Entity pEntity, float rotationYaw) {
            this.worldServerInstance.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z));
            pEntity.setPosition(this.x, this.y, this.z);
            pEntity.motionX = 0.0;
            pEntity.motionY = 0.0;
            pEntity.motionZ = 0.0;
        }
    }

}
