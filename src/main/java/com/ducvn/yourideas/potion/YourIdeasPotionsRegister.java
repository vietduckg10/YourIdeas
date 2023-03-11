package com.ducvn.yourideas.potion;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.effect.YourIdeasEffectsRegister;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YourIdeasPotionsRegister {
    public static final DeferredRegister<Potion> VANILLA_POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, YourIdeasMod.MODID);
    public static final DeferredRegister<Potion> RNG_POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        if (YourIdeasConfig.levitation_potion.get()){
            VANILLA_POTIONS.register(bus);
        }
        if (YourIdeasConfig.rng_effects.get()){
            RNG_POTIONS.register(bus);
        }
    }

    public static final RegistryObject<Potion> LEVITATION_POTION = VANILLA_POTIONS.register("levitation_potion", () ->
            new Potion(new EffectInstance(Effects.LEVITATION, 1200)));

    public static final RegistryObject<Potion> FATE_POTION = RNG_POTIONS.register("fate_potion", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.FATE.get(), 1200)));
    public static final RegistryObject<Potion> FATE_POTION_LONG = RNG_POTIONS.register("fate_potion_long", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.FATE.get(), 3600)));
    public static final RegistryObject<Potion> FATE_POTION_STRONG = RNG_POTIONS.register("fate_potion_strong", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.FATE.get(), 1200, 1)));

    public static final RegistryObject<Potion> BLESSING_POTION = RNG_POTIONS.register("blessing_potion", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.BLESSING.get(), 1200)));
    public static final RegistryObject<Potion> BLESSING_POTION_LONG = RNG_POTIONS.register("blessing_potion_long", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.BLESSING.get(), 3600)));
    public static final RegistryObject<Potion> BLESSING_POTION_STRONG = RNG_POTIONS.register("blessing_potion_strong", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.BLESSING.get(), 1200, 1)));

    public static final RegistryObject<Potion> CALAMITY_POTION = RNG_POTIONS.register("calamity_potion", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.CALAMITY.get(), 1200)));
    public static final RegistryObject<Potion> CALAMITY_POTION_LONG = RNG_POTIONS.register("calamity_potion_long", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.CALAMITY.get(), 3600)));
    public static final RegistryObject<Potion> CALAMITY_POTION_STRONG = RNG_POTIONS.register("calamity_potion_strong", () ->
            new Potion(new EffectInstance(YourIdeasEffectsRegister.CALAMITY.get(), 1200, 1)));
}
