package com.ducvn.yourideas.event;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.loot_modifier.BatToothLootModifier;
import com.ducvn.yourideas.loot_modifier.LevitationPotionLootModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YourIdeasEventBusEvent {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                           event) {
        if (YourIdeasConfig.levitation_potion.get()){
            event.getRegistry().registerAll(
                    new LevitationPotionLootModifier.Serializer().setRegistryName
                            (new ResourceLocation(YourIdeasMod.MODID,"levitation_potion_from_witch"))
            );
        }
        if (YourIdeasConfig.spear_of_siphon.get()){
            event.getRegistry().registerAll(
                    new BatToothLootModifier.Serializer().setRegistryName
                            (new ResourceLocation(YourIdeasMod.MODID,"tooth_from_bat"))
            );
        }
    }
}
