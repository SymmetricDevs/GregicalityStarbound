package com.starl0stgaming.gregicalitystarbound.common;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetworkManager;
import com.starl0stgaming.gregicalitystarbound.common.recipe.GCSBRecipeLoader;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.ModDimension;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class CommonProxy {


    public void preLoad() {
        ModDimension.init();
    }

    public void load() {

    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        GregicalityStarbound.SPACE_CONTROLLER.initializeSpace();

        IForgeRegistry<Biome> registry = event.getRegistry();

        registry.registerAll(ModDimension.BIOMES.toArray(new Biome[0]));

        ModDimension.BIOMES.forEach((biome) -> {
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biome, 4));
            BiomeManager.addSpawnBiome(biome);

            BiomeDictionary.addTypes(biome, BiomeDictionary.Type.DEAD);
        });

        GCSBLog.LOGGER.info("Registered Biomes");


    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GCSBRecipeLoader.init();
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent fmlevt) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            if (!world.isRemote) {
                TelemetryNetworkManager saveData = (TelemetryNetworkManager) world.loadData(TelemetryNetworkManager.class, TelemetryNetworkManager.DATA_NAME);
                if (saveData == null) {
                    saveData = new TelemetryNetworkManager(TelemetryNetworkManager.DATA_NAME);
                    world.setData(TelemetryNetworkManager.DATA_NAME, saveData);
                }
                TelemetryNetworkManager.setInstance(saveData);
            }
        }
    }
}
