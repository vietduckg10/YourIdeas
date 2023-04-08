package com.ducvn.yourideas.effect;

import com.ducvn.yourideas.YourIdeasMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class DivisionSightEffect extends Effect {
    protected DivisionSightEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.getPersistentData().getBoolean(YourIdeasMod.MODID + "division_sight")){
                if (player.hasEffect(YourIdeasEffectsRegister.VERDANT_SIGHT.get())){
                    player.removeEffect(YourIdeasEffectsRegister.VERDANT_SIGHT.get());
                    player.getPersistentData().putBoolean(YourIdeasMod.MODID + "verdant_sight", false);
                }
                if (player.hasEffect(YourIdeasEffectsRegister.NEGATIVE_SIGHT.get())){
                    player.removeEffect(YourIdeasEffectsRegister.NEGATIVE_SIGHT.get());
                    player.getPersistentData().putBoolean(YourIdeasMod.MODID + "negative_sight", false);
                }
                if (player.level.isClientSide) {
                    Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("shaders/post/spider.json"));
                }
                player.getPersistentData().putBoolean(YourIdeasMod.MODID + "division_sight", true);
            }
            else {
                if (player.getEffect(this).getDuration() <= 1){
                    if (player.level.isClientSide){
                        Minecraft.getInstance().gameRenderer.shutdownEffect();
                    }
                    player.getPersistentData().putBoolean(YourIdeasMod.MODID + "division_sight", false);
                    player.removeEffect(YourIdeasEffectsRegister.DIVISION_SIGHT.get());
                }
            }
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
