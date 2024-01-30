package com.starl0stgaming.gregicalitystarbound.api.recipes;

import com.starl0stgaming.gregicalitystarbound.api.recipes.builders.LaunchPadRecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class GCSBRecipeMaps {
    public static final RecipeMap<LaunchPadRecipeBuilder> LAUNCH_PAD_LOADING_RECIPES =
            new RecipeMap<>("launch_pad", 1, 0, 1, 0, new LaunchPadRecipeBuilder(), true);
}
