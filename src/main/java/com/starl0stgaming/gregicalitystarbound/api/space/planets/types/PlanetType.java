package com.starl0stgaming.gregicalitystarbound.api.space.planets.types;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.PlanetBiome;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.atmosphere.Atmosphere;

import java.util.List;

public class PlanetType {

    public boolean hasAtmosphere;
    public boolean hasRings;
    public boolean hasWeather;
    public boolean hasMoon;

    public Atmosphere atmosphere;

    private List<PlanetBiome> biomes;

    public PlanetType() {

    }

    public boolean isHasAtmosphere() {
        return hasAtmosphere;
    }

    public void setHasAtmosphere(boolean hasAtmosphere) {
        this.hasAtmosphere = hasAtmosphere;
    }

    public boolean isHasRings() {
        return hasRings;
    }

    public void setHasRings(boolean hasRings) {
        this.hasRings = hasRings;
    }

    public boolean isHasWeather() {
        return hasWeather;
    }

    public void setHasWeather(boolean hasWeather) {
        this.hasWeather = hasWeather;
    }

    public boolean isHasMoon() {
        return hasMoon;
    }

    public void setHasMoon(boolean hasMoon) {
        this.hasMoon = hasMoon;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public List<PlanetBiome> getBiomes() {
        return biomes;
    }

    public void setBiomes(List<PlanetBiome> biomes) {
        this.biomes = biomes;
    }
}
