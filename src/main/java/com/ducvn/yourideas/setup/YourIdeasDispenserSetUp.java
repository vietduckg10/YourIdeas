package com.ducvn.yourideas.setup;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.Random;

public class YourIdeasDispenserSetUp {

    public static void registerBehavior(){
        if (YourIdeasConfig.dragon_charge.get()){
            DispenserBlock.registerBehavior(YourIdeasItemsRegister.DRAGON_CHARGE.get(), new DefaultDispenseItemBehavior() {
                public ItemStack execute(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
                    Direction direction = p_82487_1_.getBlockState().getValue(DispenserBlock.FACING);
                    IPosition iposition = DispenserBlock.getDispensePosition(p_82487_1_);
                    double d0 = iposition.x() + (double)((float)direction.getStepX() * 0.3F);
                    double d1 = iposition.y() + (double)((float)direction.getStepY() * 0.3F);
                    double d2 = iposition.z() + (double)((float)direction.getStepZ() * 0.3F);
                    World world = p_82487_1_.getLevel();
                    Random random = world.random;
                    double d3 = random.nextGaussian() * 0.05D + (double)direction.getStepX();
                    double d4 = random.nextGaussian() * 0.05D + (double)direction.getStepY();
                    double d5 = random.nextGaussian() * 0.05D + (double)direction.getStepZ();
                    DragonFireballEntity dragonFireball = new DragonFireballEntity(world, d0, d1, d2, d3, d4, d5);
                    Util.make(p_82487_2_.copy(), (p_213897_0_) -> {
                        p_213897_0_ = YourIdeasItemsRegister.DRAGON_CHARGE.get().getDefaultInstance();
                        p_213897_0_.setCount(1);
                    });
                    p_82487_2_.shrink(1);
                    world.addFreshEntity(dragonFireball);
                    return p_82487_2_;
                }

                protected void playSound(IBlockSource p_82485_1_) {
                    p_82485_1_.getLevel().levelEvent(1018, p_82485_1_.getPos(), 0);
                }
            });
        }
    }
}
