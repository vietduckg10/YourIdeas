package com.ducvn.yourideas.entity;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import com.ducvn.yourideas.entity.slimeball.ThrowableSlimeBallEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YourIdeasEntitiesRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIE_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, YourIdeasMod.MODID);

    public static void init(IEventBus eventBus){
        ENTITIE_TYPES.register(eventBus);
    }

    public static final RegistryObject<EntityType<ThrowableBrickEntity>> THROWABLE_BRICK = ENTITIE_TYPES.register("throwable_brick", () ->
            EntityType.Builder.of((EntityType.IFactory<ThrowableBrickEntity>) ThrowableBrickEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("throwable_brick"));

    public static final RegistryObject<EntityType<ThrowableSlimeBallEntity>> THROWABLE_SLIME_BALL = ENTITIE_TYPES.register("throwable_slime_ball", () ->
            EntityType.Builder.of((EntityType.IFactory<ThrowableSlimeBallEntity>) ThrowableSlimeBallEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("throwable_slime_ball"));
}
