package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class SpaceBiome extends Biome {
    public SpaceBiome(Biome.BiomeProperties properties) {
        super(properties);

        fillerBlock = Blocks.AIR.getDefaultState();
        topBlock = Blocks.AIR.getDefaultState();
    }
}
