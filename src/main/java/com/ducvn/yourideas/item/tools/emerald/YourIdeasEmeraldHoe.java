package com.ducvn.yourideas.item.tools.emerald;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.tools.YourIdeasItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class YourIdeasEmeraldHoe extends HoeItem {

    public YourIdeasEmeraldHoe(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (this.getTier().equals(YourIdeasItemTier.EMERALD_BASE_NETHERITE)){
            tooltip.add(new TranslationTextComponent(
                            "\u00A7aEmerald\u00A7r"
                    )
            );
        }
        tooltip.add(new TranslationTextComponent(
                        "\u00A7a30% drop bone or bone meal when break grass\u00A7r"
                )
        );
    }
    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity user) {
        if (!world.isClientSide && YourIdeasConfig.emerald_gears.get()){
            if (state.getBlock() instanceof GrassBlock
            || state.getBlock() instanceof TallGrassBlock){
                Random roll = new Random();
                if (roll.nextInt(100) < 30){
                    user.addEffect(new EffectInstance(Effects.SATURATION, 300));
                    ItemEntity itemEntity;
                    if (roll.nextBoolean()){
                        itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), Items.BONE.getDefaultInstance());
                    }
                    else {
                        itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), Items.BONE_MEAL.getDefaultInstance());
                    }
                    itemEntity.spawnAtLocation(itemEntity.getItem());
                }
            }
        }
        return super.mineBlock(stack, world, state, pos, user);
    }
}
