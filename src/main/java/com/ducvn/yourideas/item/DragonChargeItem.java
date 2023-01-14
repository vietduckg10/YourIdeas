package com.ducvn.yourideas.item;

import com.ducvn.yourideas.config.YourIdeasConfig;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DragonChargeItem extends Item {

    public DragonChargeItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World level = context.getLevel();
        if (!level.isClientSide && YourIdeasConfig.dragon_charge.get()){
            BlockPos pos = context.getClickedPos().above();
            PlayerEntity player = context.getPlayer();
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(level, pos.getX(), pos.getY(), pos.getZ());
            areaeffectcloudentity.setOwner(player);
            areaeffectcloudentity.setParticle(ParticleTypes.DRAGON_BREATH);
            areaeffectcloudentity.setRadius(2.0F);
            areaeffectcloudentity.setDuration(300);
            areaeffectcloudentity.setRadiusPerTick((5.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.addEffect(new EffectInstance(Effects.HARM, 1, 0));
            level.addFreshEntity(areaeffectcloudentity);
            if (!player.isCreative()){
                context.getItemInHand().shrink(1);
            }
            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

        }
        return super.useOn(context);
    }
}
