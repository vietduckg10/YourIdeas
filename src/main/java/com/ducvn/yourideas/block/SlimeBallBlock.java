package com.ducvn.yourideas.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import java.util.Random;

public class SlimeBallBlock extends LadderBlock{
    public SlimeBallBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random p_225542_4_) {
        world.destroyBlock(pos, true);
    }
}
