package com.ducvn.yourideas.effect;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YourIdeasEffectsRegister {
    public static final DeferredRegister<Effect> RNG_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, YourIdeasMod.MODID);
    public static final DeferredRegister<Effect> SIGHT_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, YourIdeasMod.MODID);
    public static void init(IEventBus eventBus) {
        if (YourIdeasConfig.rng_effects.get()){
            RNG_EFFECTS.register(eventBus);
        }
        if (YourIdeasConfig.sight_effects.get()){
            SIGHT_EFFECTS.register(eventBus);
        }
    }

    public static final RegistryObject<Effect> FATE = RNG_EFFECTS.register("fate", () ->
            new FateEffect(EffectType.NEUTRAL, 16770165)
    );

    public static final RegistryObject<Effect> BLESSING = RNG_EFFECTS.register("blessing", () ->
            new BlessingEffect(EffectType.BENEFICIAL, 6745228)
    );

    public static final RegistryObject<Effect> CALAMITY = RNG_EFFECTS.register("calamity", () ->
            new CalamityEffect(EffectType.HARMFUL, 6052956)
    );

    public static final RegistryObject<Effect> NEGATIVE_SIGHT = SIGHT_EFFECTS.register("negative_sight", () ->
            new NegativeSightEffect(EffectType.NEUTRAL, 16106239)
    );

    public static final RegistryObject<Effect> VERDANT_SIGHT = SIGHT_EFFECTS.register("verdant_sight", () ->
            new VerdantSightEffect(EffectType.NEUTRAL, 5439337)
    );

    public static final RegistryObject<Effect> DIVISION_SIGHT = SIGHT_EFFECTS.register("division_sight", () ->
            new DivisionSightEffect(EffectType.NEUTRAL, 16753571)
    );
}
