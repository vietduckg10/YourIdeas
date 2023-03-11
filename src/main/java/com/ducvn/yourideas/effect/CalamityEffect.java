package com.ducvn.yourideas.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CalamityEffect extends Effect {
    protected CalamityEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    private static List<Integer> badEffectId = new ArrayList<>(
            Arrays.asList(
                    2, 4, 9, 15, 17, 18, 19,
                    20, 24, 25, 27, 31
            )
    );

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide){
            Random roll = new Random();
            int effectId = badEffectId.get(roll.nextInt(badEffectId.size()));
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
