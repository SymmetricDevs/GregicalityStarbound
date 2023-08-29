package com.starl0stgaming.gregicalitystarbound.api.world.dimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class DummyBiome extends Biome {

    private int waterColor;
    private int skyColor;
    public DummyBiome(BiomeProperties properties, IBlockState filler, int waterColor, int skyColor) {
        super(properties);
        this.waterColor = waterColor;
        this.skyColor = skyColor;
        fillerBlock = filler;
    }

    @Override
    public void decorate(World par1World, Random par2Random, BlockPos pos) {
        super.decorate(par1World, par2Random, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float par1)
    {
        return skyColor;
    }
}
