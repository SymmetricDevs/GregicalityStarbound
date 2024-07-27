package com.starl0stgaming.gregicalitystarbound.common.space.dimension;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.PlanetBiome;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GCSBDimensionManager {
    private static List<Biome> biomeList = new ArrayList<>();


    private static Int2ObjectMap<WorldGenDetails> worldGenDetailsList = new Int2ObjectOpenHashMap<>();

    public static List<Biome> getBiomeList() {
        return biomeList;
    }

    public static Int2ObjectMap<WorldGenDetails> getWorldGenDetailsList() {
        return worldGenDetailsList;
    }

    public static void addDetailsTolist(int id, WorldGenDetails details) {
        worldGenDetailsList.put(id, details);
    }

    public static WorldGenDetails getSpecificWorldGenDetails(Integer s) {
        return worldGenDetailsList.get(s);
    }

    public static List<Biome> getBiomeListFromDetails(WorldGenDetails details) {
        PlanetBiome[] pbiomes = details.getBiomeList();
        List<Biome> biomes = new ArrayList<>();
        Arrays.stream(pbiomes).forEach((biome) -> {
            biomes.add(biome.getBiome());
        });

        return biomes;
    }

}
