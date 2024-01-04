package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RocketEntity extends Entity {
    public RocketEntity(World worldIn) {
        super(worldIn);
    }

    public RocketEntity(World worldIn, double x, double y, double z) {
        super(worldIn);

    }

    public RocketEntity(World worldIn, BlockPos pos) {
        this(worldIn, (float) pos.getX() + 0.5F, pos.getY(), (float) pos.getZ() + 0.5F);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
