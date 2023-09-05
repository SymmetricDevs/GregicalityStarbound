package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.GCSBDimensionManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.List;

public class DummyWorldProvider extends WorldProvider {

    private WorldGenDetails details;
    public WorldGenDetails getDimensionInformation() {
        int dim = getDimension();
        details = GCSBDimensionManager.getSpecificWorldGenDetails(dim);

        return GCSBDimensionManager.getSpecificWorldGenDetails(dim);
    }

    @Override
    protected void init() {
        biomeProvider = new DummyBiomeProvider(GCSBDimensionManager.getBiomeListFromDetails(details));
    }
    @Override
    public IChunkGenerator createChunkGenerator() {
        getDimensionInformation();
        if (details != null) {
            return new DummyChunkGenerator(world, details.getStone(), details.getBedrock());
        }

        return new ChunkGeneratorFlat(world, world.getSeed(), false, "");
    }

    @Override
    public int getAverageGroundLevel() {
        getDimensionInformation();
        if (details != null) {
            return details.getAverageGroundLevel();
        }

        return 50;
    }

    // TODO
    // make sure to add planet name to this
    @Override
    public String getSaveFolder() {
        return details.getName();
    }

    @Override
    public DimensionType getDimensionType() {
        return null;
    }

}
