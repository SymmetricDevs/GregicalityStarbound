package com.starl0stgaming.gregicalitystarbound.api.space.planets;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.world.dimension.DummyBiome;
import com.starl0stgaming.gregicalitystarbound.api.world.dimension.DummyWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class Planet {

    //Defining variables
    private String planetName;
    private int id;
    private int dimID;

    private List<DummyBiome> biomes;
    private boolean isLoaded;

    private DimensionType PlanetDimension;




    //Atmosphere

    public Planet(int id, String planetName) {
        this.id = id;
        this.planetName = planetName;
    }

    public Planet(int id, int dimID, String planetName) {
        this.id = id;
        this.dimID = dimID;
        this.planetName = planetName;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void load() {
        if(!isLoaded) {
            isLoaded = true;
            if (this.dimID != 0) {
                PlanetDimension = DimensionType.register(this.planetName, "_gcsb", this.dimID, DummyWorldProvider.class, false);
            }
            GCSBLog.LOGGER.info("Loaded Planet with ID " + this.getId() + " and name " + this.getPlanetName());
        }
    }

    public void unload() {
        if(isLoaded) {
            isLoaded = false;
            GCSBLog.LOGGER.info("Unloaded Planet with ID " + this.getId() + " and name " + this.getPlanetName());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDimID() {
        return dimID;
    }

    public void setDimID(int dimID) {
        this.dimID = dimID;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public void setBiomes(List<DummyBiome> biomes) {
        this.biomes = biomes;
    }

    public List<DummyBiome> getBiomes() {
        return biomes;
    }

    public DimensionType getDimension() {
        return PlanetDimension;
    }
    @Override
    public String toString() {
        return "Planet Name: " + this.getPlanetName() + " Planet Id: " + this.getId() + " Planet DIM ID: " + this.getDimID();
    }
}
