package com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen;

import com.google.gson.JsonObject;
import com.starl0stgaming.gregicalitystarbound.api.util.StringUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class WorldGenDetails {

    public String name;
    public int averageGroundLevel;
    public List<PlanetBiome> biomeList;
    public IBlockState stone;
    public IBlockState bedrock;


    public int getAverageGroundLevel() {
       return averageGroundLevel;
    }
    public void setAverageGroundLevel(int averageGroundLevel) {
        this.averageGroundLevel = averageGroundLevel;
    }

    public List<PlanetBiome> getBiomeList() {
        return this.biomeList;
    }

    public void setBiomeList(List<PlanetBiome> biomeList) {
        this.biomeList = biomeList;
    }

    public IBlockState getStone() {
        return this.stone;
    }
    public void setStone(String stone) {
        this.stone = StringUtil.getBlockfromString(stone).getDefaultState();
    }

    public IBlockState getBedrock() {
        return this.bedrock;
    }
    public void setBedrock(String bedrock) {
        this.bedrock = StringUtil.getBlockfromString(bedrock).getDefaultState();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
