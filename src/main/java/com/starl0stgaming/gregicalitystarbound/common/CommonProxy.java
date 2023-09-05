package com.starl0stgaming.gregicalitystarbound.common;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import com.starl0stgaming.gregicalitystarbound.common.recipe.GCSBRecipeLoader;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.event.PostMaterialEvent;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class CommonProxy {

    public void preLoad() {


        GregicalityStarbound.SPACE_CONTROLLER.initializeSpace();
    }


    @SubscribeEvent
    public void registerBiome(RegistryEvent.Register<Biome> event) {
        IForgeRegistry<Biome> registry = event.getRegistry();


    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GCSBRecipeLoader.init();
    }
}
