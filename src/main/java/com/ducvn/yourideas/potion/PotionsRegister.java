package com.ducvn.yourideas.potion;

import com.ducvn.yourideas.YourIdeasMod;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionsRegister {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        POTIONS.register(bus);
    }

    public static final RegistryObject<Potion> LEVITATION_POTION = POTIONS.register("levitation_potion", () ->
            new Potion(new EffectInstance(Effects.LEVITATION, 1200)));
}
