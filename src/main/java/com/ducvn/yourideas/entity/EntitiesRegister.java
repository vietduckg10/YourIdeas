package com.ducvn.yourideas.entity;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntitiesRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIE_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, YourIdeasMod.MODID);

    public static void init(IEventBus eventBus){
        ENTITIE_TYPES.register(eventBus);
    }

    public static final RegistryObject<EntityType<ThrowableBrickEntity>> THROWABLE_BRICK = ENTITIE_TYPES.register("throwable_brick", () ->
            EntityType.Builder.of((EntityType.IFactory<ThrowableBrickEntity>) ThrowableBrickEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("throwable_brick"));
}
