package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class DummyGenLayer extends GenLayer {

    private Biome[] biomes;

    public DummyGenLayer(Biome[] biomes, long seed, GenLayer parent) {
        super(seed);
        parent = parent;
        this.biomes = biomes;
    }

    public DummyGenLayer(Biome[] biomes, long seed) {
        super(seed);
        this.biomes = biomes;
    }

    @Override
    public int[] getInts(int x, int y, int width, int depth) {
        int dest[] = IntCache.getIntCache(width * depth);
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
                initChunkSeed(dx + x, dz + y);
                dest[dx + dz * depth] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);

            }
        }
        return dest;
    }
}
