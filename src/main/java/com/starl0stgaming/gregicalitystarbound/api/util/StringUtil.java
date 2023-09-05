package com.starl0stgaming.gregicalitystarbound.api.util;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StringUtil {

    public static Block getBlockfromString(String s) {
        String modId = "minecraft";

        String name;

        if (s.contains(":")) {
            modId = s.substring(0, s.indexOf(":"));
        }

        name = s.substring(s.indexOf(":") + 1);

        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, name));
    }
}
