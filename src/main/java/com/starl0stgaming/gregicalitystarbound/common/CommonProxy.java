package com.starl0stgaming.gregicalitystarbound.common;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import com.starl0stgaming.gregicalitystarbound.common.recipe.GCSBRecipeLoader;
import gregtech.api.GregTechAPI;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class CommonProxy {


    public void preLoad() {


        GregicalityStarbound.PLANET_SYSTEM_MANAGER.init();
    }


    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event) {
        if(GregicalityStarbound.keyBinding.isPressed()) {
            for(int i = 0; i < GregicalityStarbound.PLANET_SYSTEM_MANAGER.getPlanetList().size(); i++) {
                Planet planet = GregicalityStarbound.PLANET_SYSTEM_MANAGER.getPlanetList().get(i);

                System.out.println(planet.getPlanetName());
                System.out.println(planet.getId());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerMaterials(GregTechAPI.MaterialEvent event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GCSBRecipeLoader.init();
    }
}
