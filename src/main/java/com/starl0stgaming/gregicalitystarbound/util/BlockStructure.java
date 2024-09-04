package com.starl0stgaming.gregicalitystarbound.util;

import com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad.MetaTileEntityLaunchPad;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.world.DummyWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BlockStructure implements IBlockAccess {
    private HashMap<BlockPos,IBlockState> blockList;
    private HashMap<BlockPos,TileEntity> teList;
    private Pair<Vec3i,Vec3i> bounds;
    private DummyWorld world;
    private Chunk chunk;
    private BlockPos centralPos;
    private Set<MetaTileEntity> infraComponents;
    private boolean isValid;
    private double weight = 0;

    public BlockStructure(Pair<Vec3i, Vec3i> bounds) {
        this.isValid = false;
        this.world = new DummyWorld();
        this.chunk = new Chunk(world, 0,0);
        this.blockList = new HashMap<BlockPos,IBlockState>();
        this.teList = new HashMap<BlockPos,TileEntity>();
        this.bounds = bounds;
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return teList.get(pos);
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return lightValue;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return blockList.get(pos);
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return blockList.get(pos).getMaterial() == Material.AIR;
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return world.getBiome(pos.add(centralPos));
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction) {
        return getBlockState(pos).getStrongPower(this,pos,direction);
    }

    @Override
    public WorldType getWorldType() {
        return world.getWorldType();
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return getBlockState(pos).isSideSolid(world, pos, side);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ByteBuf writeOut(ByteBuf bb) {
        return bb;
    }

    public void readBuffer(ByteBuf bb) {

    }
}
