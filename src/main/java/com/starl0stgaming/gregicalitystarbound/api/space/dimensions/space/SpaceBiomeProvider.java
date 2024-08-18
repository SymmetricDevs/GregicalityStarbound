package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

public class SpaceBiomeProvider extends BiomeProvider {
    @Override
    public Biome getBiome(BlockPos pos) {
        return SpaceWorldProvider.biome;
    }

    @Override
    public Biome getBiome(BlockPos pos, Biome defaultBiome) {
        return SpaceWorldProvider.biome;
    }
}
