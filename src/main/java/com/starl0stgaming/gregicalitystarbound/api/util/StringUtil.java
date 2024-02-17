package com.starl0stgaming.gregicalitystarbound.api.util;

import gregtech.api.GTValues;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StringUtil {

    public static ResourceLocation gcsbId(String name) {
        return new ResourceLocation(GTValues.MODID, name);
    }

    public static IBlockState getBlockfromString(String s) {
        String[] parts = s.split(":");

        if (parts.length < 2) {
            return null;
        }
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parts[0], parts[1]));
        if (block == null) return null;
        int meta = 0;
        if (parts.length > 2) {
            try {
                meta = Integer.parseInt(parts[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        IBlockState state = block.getDefaultState();
        if (meta != 0) {
            state = block.getStateFromMeta(meta);
        }

        return state;

    }
}
