package com.ducvn.yourideas.item.tools.emerald;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.tools.YourIdeasItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class YourIdeasEmeraldAxe extends AxeItem {

    public YourIdeasEmeraldAxe(IItemTier p_i48530_1_, float p_i48530_2_, float p_i48530_3_, Properties p_i48530_4_) {
        super(p_i48530_1_, p_i48530_2_, p_i48530_3_, p_i48530_4_);
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
                        "\u00A7a10% give 15s of Haste\u00A7r"
                )
        );
    }
    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState p_179218_3_, BlockPos pos, LivingEntity user) {
        if (!world.isClientSide && YourIdeasConfig.emerald_gears.get()){
            Random roll = new Random();
            if (roll.nextInt(100) < 10){
                user.addEffect(new EffectInstance(Effects.DIG_SPEED, 300));
            }
        }
        return super.mineBlock(stack, world, p_179218_3_, pos, user);
    }
}
