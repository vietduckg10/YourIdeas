package com.ducvn.yourideas.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.Random;

public class FateEffect extends Effect {
    protected FateEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide){
            Random roll = new Random();
            int effectId = roll.nextInt(32) + 1;
            while (6 == effectId || 7 == effectId){
                effectId = roll.nextInt(32) + 1;
            }
            entity.addEffect(new EffectInstance(
                    Effect.byId(effectId),
                    300,
                    amplifier));
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 100 == 0 ? true : false;
    }
}
