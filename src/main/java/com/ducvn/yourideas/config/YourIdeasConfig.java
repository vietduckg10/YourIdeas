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

    static {
        BUILDER.push("Your Ideas Mod Config");

        throwable_brick = BUILDER.define("Throwable Brick", true);
        plantable_poisonous_potato = BUILDER.define("Plantable Poisonous Potato", true);
        mixing_potions = BUILDER.define("Mixing Potions", true);
        throw_tnt_from_hand = BUILDER.define("Throw TNT From Hand", true);
        levitation_potion = BUILDER.define("Levitation Potion", true);
        dragon_charge = BUILDER.define("Dragon Charge", true);

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
