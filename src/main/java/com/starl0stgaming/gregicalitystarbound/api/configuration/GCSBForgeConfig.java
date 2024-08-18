package com.starl0stgaming.gregicalitystarbound.api.configuration;

import com.starl0stgaming.gregicalitystarbound.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MODID, name = Tags.MODID + '/' + Tags.MODID)
public class GCSBForgeConfig {

    @Config.Comment("Load default planets")
    @Config.Name("Load default planets")
    public static boolean loadDefaultPlanets = true;

    @Config.Comment("Space dimension ID")
    @Config.Name("Space dimension ID")
    public static int spaceDimensionID = 1649;
}
