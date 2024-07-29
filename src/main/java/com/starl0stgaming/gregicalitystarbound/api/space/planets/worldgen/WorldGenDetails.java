package com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen;

public class WorldGenDetails {

    public String name;
    public int averageGroundLevel;
    public PlanetBiome[] biomeList;
    public String stone;
    public String bedrock;

    public int getAverageGroundLevel() {
        return averageGroundLevel;
    }

    public void setAverageGroundLevel(int averageGroundLevel) {
        this.averageGroundLevel = averageGroundLevel;
    }

    public PlanetBiome[] getBiomeList() {
        return this.biomeList;
    }

    public void setBiomeList(PlanetBiome[] biomeList) {
        this.biomeList = biomeList;
    }

    public String getStone() {
        return this.stone;
    }

    public void setStone(String stone) {
        this.stone = stone;
    }

    public String getBedrock() {
        return this.bedrock;
    }

    public void setBedrock(String bedrock) {
        this.bedrock = bedrock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
