package com.ducvn.yourideas.setup;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.potion.YourIdeasPotionsRegister;
import com.ducvn.yourideas.util.YourIdeasBrewingRecipe;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class YourIdeasBrewingSetUp {
    public static void registerBrewingRecipes(){
        if (YourIdeasConfig.rng_effects.get()){
            //Fate
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    Potions.AWKWARD, Items.NAUTILUS_SHELL, YourIdeasPotionsRegister.FATE_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.FATE_POTION_LONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION.get(), Items.GLOWSTONE_DUST, YourIdeasPotionsRegister.FATE_POTION_STRONG.get()));
            //Bessing
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION.get(), Items.EMERALD, YourIdeasPotionsRegister.BLESSING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.BLESSING_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.BLESSING_POTION_LONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.BLESSING_POTION.get(), Items.GLOWSTONE_DUST, YourIdeasPotionsRegister.BLESSING_POTION_STRONG.get()));
            //Calamity
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION.get(), Items.GHAST_TEAR, YourIdeasPotionsRegister.CALAMITY_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.CALAMITY_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.CALAMITY_POTION_LONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.CALAMITY_POTION.get(), Items.GLOWSTONE_DUST, YourIdeasPotionsRegister.CALAMITY_POTION_STRONG.get()));
            //Fate -> Blessing/Calamity (long, strong)
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION_LONG.get(), Items.EMERALD, YourIdeasPotionsRegister.BLESSING_POTION_LONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION_STRONG.get(), Items.EMERALD, YourIdeasPotionsRegister.BLESSING_POTION_STRONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION_LONG.get(), Items.GHAST_TEAR, YourIdeasPotionsRegister.CALAMITY_POTION_LONG.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.FATE_POTION_STRONG.get(), Items.GHAST_TEAR, YourIdeasPotionsRegister.CALAMITY_POTION_STRONG.get()));
            //Negative
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    Potions.NIGHT_VISION, Items.ENDER_EYE, YourIdeasPotionsRegister.NEGATIVE_SIGHT_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.NEGATIVE_SIGHT_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.NEGATIVE_SIGHT_POTION_LONG.get()));
            //Division
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    Potions.NIGHT_VISION, Items.SPIDER_EYE, YourIdeasPotionsRegister.DIVISION_SIGHT_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.DIVISION_SIGHT_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.DIVISION_SIGHT_POTION_LONG.get()));
            //Verdant
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    Potions.NIGHT_VISION, Items.EMERALD, YourIdeasPotionsRegister.VERDANT_SIGHT_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new YourIdeasBrewingRecipe(
                    YourIdeasPotionsRegister.VERDANT_SIGHT_POTION.get(), Items.REDSTONE, YourIdeasPotionsRegister.VERDANT_SIGHT_POTION_LONG.get()));
        }
    }
}
