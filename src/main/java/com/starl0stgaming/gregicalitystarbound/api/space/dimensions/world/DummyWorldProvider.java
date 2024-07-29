package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.IChunkGenerator;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import com.starl0stgaming.gregicalitystarbound.api.util.StringUtil;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.GCSBDimensionManager;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.ModDimension;

public class DummyWorldProvider extends WorldProvider {

    private WorldGenDetails details;

    public WorldGenDetails getDimensionInformation() {
        int dim = getDimension();
        details = GCSBDimensionManager.getSpecificWorldGenDetails(dim);

        return GCSBDimensionManager.getSpecificWorldGenDetails(dim);
    }

    @Override
    protected void init() {
        biomeProvider = new DummyBiomeProvider(world,
                GCSBDimensionManager.getBiomeListFromDetails(getDimensionInformation()));
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        getDimensionInformation();
        if (details != null) {
            return new DummyChunkGenerator(world, world.getSeed(), StringUtil.getBlockfromString(details.getStone()),
                    StringUtil.getBlockfromString(details.getBedrock()));
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
        getDimensionInformation();
        if (details != null) {
            return details.getName();
        }
        return "ERROR";
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimension.planetType;
    }
}
