package com.starl0stgaming.gregicalitystarbound.common;

import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;
import com.starl0stgaming.gregicalitystarbound.common.network.NetworkUtil;
import gregtech.api.util.GTTeleporter;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetworkManager;
import com.starl0stgaming.gregicalitystarbound.common.recipe.GCSBRecipeLoader;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.ModDimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class CommonProxy {
    public static Map<EntityRocket, EntityPlayer> teleportingPlayers = new Object2ObjectArrayMap<>();


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
    public static void registerItems(RegistryEvent.Register<Item> event) {}

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GCSBRecipeLoader.init();
    }

    public void preLoad() {
        ModDimension.init();
        NetworkUtil.registerPackets();
    }

    public void load() {}

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent fmlevt) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            if (!world.isRemote) {
                TelemetryNetworkManager saveData = (TelemetryNetworkManager) world
                        .loadData(TelemetryNetworkManager.class, TelemetryNetworkManager.DATA_NAME);
                if (saveData == null) {
                    saveData = new TelemetryNetworkManager(TelemetryNetworkManager.DATA_NAME);
                    world.setData(TelemetryNetworkManager.DATA_NAME, saveData);
                }
                TelemetryNetworkManager.setInstance(saveData);
            }
        }
    }

    @SubscribeEvent
    public static void onTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!teleportingPlayers.isEmpty()) {
                List<EntityRocket> toRemove = new ArrayList<>();
                for (Map.Entry<EntityRocket, EntityPlayer> pair : teleportingPlayers.entrySet()) {
                    EntityRocket rocket = pair.getKey();
                    EntityPlayer player = pair.getValue();
                    if (rocket.dimension != player.dimension && player.getServer() != null) {
                        WorldServer newWorld = player.getServer().getWorld(rocket.dimension);

                        player.setLocationAndAngles(rocket.getPosition().getX(),
                                rocket.getPosition().getY(),
                                rocket.getPosition().getZ(),
                                rocket.rotationYaw,
                                rocket.rotationPitch);
                        player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, rocket.dimension,
                                new GTTeleporter(newWorld, rocket.getPosition().getX(), rocket.getPosition().getY(), rocket.getPosition().getZ()));
                        player.startRiding(rocket);
                        toRemove.add(rocket);
                    }
                }

                for (EntityRocket rocket : toRemove) {
                    teleportingPlayers.remove(rocket);
                }
            }
        }
    }
}
