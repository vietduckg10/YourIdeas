package com.ducvn.yourideas.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlessingEffect extends Effect {
    protected BlessingEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    private static List<Integer> goodEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 13, 14, 16,
                    21, 22, 23, 26, 28, 29, 30, 32
            )
    );

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide){
            Random roll = new Random();
            int effectId = goodEffectId.get(roll.nextInt(goodEffectId.size()));
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
