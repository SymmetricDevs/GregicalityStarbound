package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.List;

public class DummyWorldProvider extends WorldProvider {

    public List<Biome> biomeList;
    public List<Biome.SpawnListEntry> mobs;
    public IBlockState stone;
    public IBlockState bedrock;

    public int averageGroundLevel;
    public DimensionType dimensionType;

    public DummyWorldProvider(List<Biome> biomeList, List<Biome.SpawnListEntry> mobs, IBlockState stone, IBlockState bedrock, int averageGroundLevel) {
        this.biomeList = biomeList;
        this.mobs = mobs;
        this.stone = stone;
        this.bedrock = bedrock;
        this.averageGroundLevel = averageGroundLevel;
        this.dimensionType = dimensionType;
    }



    @Override
    protected void init() {
        biomeProvider = new DummyBiomeProvider(biomeList);
    }
    @Override
    public IChunkGenerator createChunkGenerator() {
        return new DummyChunkGenerator(world, mobs, stone, bedrock);
    }

    @Override
    public int getAverageGroundLevel() {
        return 50;
    }

    @Override
    public DimensionType getDimensionType() {
        return null;
    }

    // TODO
    // make sure to add planet name to this
    @Override
    public String getSaveFolder() {
        return "";
    }

}
