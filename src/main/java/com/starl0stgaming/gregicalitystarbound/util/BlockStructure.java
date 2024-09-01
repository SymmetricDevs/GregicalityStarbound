package com.starl0stgaming.gregicalitystarbound.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockStructure implements IBlockAccess {
    private HashMap<BlockPos,IBlockState> blockList;
    private HashMap<BlockPos,TileEntity> teList;
    private World world;
    private BlockPos centralPos;

    public BlockStructure(World world) {
        this.world = world;
        this.blockList = new HashMap<BlockPos,IBlockState>();
        this.teList = new HashMap<BlockPos,TileEntity>();
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return teList.get(pos);
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return 0;
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
        return 0;
    }

    @Override
    public WorldType getWorldType() {
        return world.getWorldType();
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return getBlockState(pos).isSideSolid(world, pos, side);
    }

}
