package com.starl0stgaming.gregicalitystarbound.common.space.dimension;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.PlanetBiome;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;

public class GCSBDimensionManager {
    private static List<Biome> biomeList = new ArrayList<>();


    private static Object2ObjectMap<Integer, WorldGenDetails> worldGenDetailsList = new Object2ObjectOpenHashMap<>();

    public static List<Biome> getBiomeList() {
        return biomeList;
    }

    public static Object2ObjectMap<Integer, WorldGenDetails> getWorldGenDetailsList() {
        return worldGenDetailsList;
    }

    public static void addDetailsTolist(int id, WorldGenDetails details) {
        worldGenDetailsList.put(id, details);
    }

    public static WorldGenDetails getSpecificWorldGenDetails(Integer s) {
        return worldGenDetailsList.get(s);
    }

    public static List<Biome> getBiomeListFromDetails(WorldGenDetails details) {
        List<PlanetBiome> pbiomes = details.getBiomeList();
        List<Biome> biomes = new ArrayList<>();
        pbiomes.forEach((biome) -> {
            biomes.add(biome.getBiome());
        });

        return biomes;
    }

}
