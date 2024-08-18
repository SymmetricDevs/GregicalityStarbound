package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class SpaceWorldType extends WorldType {
    /**
     * Creates a new world type, the ID is hidden and should not be referenced by modders.
     * It will automatically expand the underlying workdType array if there are no IDs left.
     */
    public SpaceWorldType() {
        super("space");
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        return new SpaceChunkGenerator(world, SpaceWorldProvider.biome);
    }

    @Override
    public double getHorizon(World world) {
        return 0;
    }
}
