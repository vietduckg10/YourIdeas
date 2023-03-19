package com.ducvn.yourideas.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class YourIdeasConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> throwable_brick;
    public static final ForgeConfigSpec.ConfigValue<Boolean> plantable_poisonous_potato;
    public static final ForgeConfigSpec.ConfigValue<Boolean> mixing_potions;
    public static final ForgeConfigSpec.ConfigValue<Boolean> throw_tnt_from_hand;
    public static final ForgeConfigSpec.ConfigValue<Boolean> levitation_potion;
    public static final ForgeConfigSpec.ConfigValue<Boolean> dragon_charge;
    public static final ForgeConfigSpec.ConfigValue<Boolean> auto_replace_totem;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spear_of_siphon;
    public static final ForgeConfigSpec.ConfigValue<Boolean> gravel_drop_nuggets;
    public static final ForgeConfigSpec.ConfigValue<Boolean> campfire_heal;
    public static final ForgeConfigSpec.ConfigValue<Boolean> edible_items;
    public static final ForgeConfigSpec.ConfigValue<Boolean> throwable_slimeball;
    public static final ForgeConfigSpec.ConfigValue<Boolean> leather_boots_speed;
    public static final ForgeConfigSpec.ConfigValue<Boolean> rng_effects;
    public static final ForgeConfigSpec.ConfigValue<Boolean> sunglasses;

    static {
        BUILDER.push("Your Ideas Mod Config");

        throwable_brick = BUILDER.define("Throwable Brick", true);
        plantable_poisonous_potato = BUILDER.define("Plantable Poisonous Potato", true);
        mixing_potions = BUILDER.define("Mixing Potions", true);
        throw_tnt_from_hand = BUILDER.define("Throw TNT From Hand", true);
        levitation_potion = BUILDER.define("Levitation Potion", true);
        dragon_charge = BUILDER.define("Dragon Charge", true);
        auto_replace_totem = BUILDER.define("Auto Replace Totem", true);
        spear_of_siphon = BUILDER.define("Spear of Siphon", true);
        gravel_drop_nuggets = BUILDER.define("Gravel drop Nuggets", true);
        campfire_heal = BUILDER.define("Campfire Heal", true);
        edible_items = BUILDER.define("Edible Vanilla Items", true);
        throwable_slimeball = BUILDER.define("Throwable Slimeball", true);
        leather_boots_speed = BUILDER.define("Leather Boots Increase Speed in Biomes", true);
        rng_effects = BUILDER.define("Fate, Blessing and Calamity Effect", true);
        sunglasses = BUILDER.define("Sunglasses", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void load(Path path) {
        final CommentedFileConfig config = CommentedFileConfig.builder(path).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        config.load();
        SPEC.setConfig(config);
    }

}
