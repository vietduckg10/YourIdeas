package com.ducvn.yourideas.block;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Queue;

public class ForeverDrySponge extends SpongeBlock {
    public ForeverDrySponge(Properties p_i48325_1_) {
        super(p_i48325_1_);
    }

    @Override
    protected void tryAbsorbWater(World p_196510_1_, BlockPos p_196510_2_) {
        if (YourIdeasConfig.forever_dry_sponge.get()){
            if (this.removeWaterBreadthFirstSearch(p_196510_1_, p_196510_2_)) {
                p_196510_1_.levelEvent(2001, p_196510_2_, Block.getId(Blocks.WATER.defaultBlockState()));
            }
        }
        else {
            super.tryAbsorbWater(p_196510_1_, p_196510_2_);
        }
    }

    private boolean removeWaterBreadthFirstSearch(World p_176312_1_, BlockPos p_176312_2_) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(p_176312_2_, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int j = tuple.getB();

            for(Direction direction : Direction.values()) {
                BlockPos blockpos1 = blockpos.relative(direction);
                BlockState blockstate = p_176312_1_.getBlockState(blockpos1);
                FluidState fluidstate = p_176312_1_.getFluidState(blockpos1);
                Material material = blockstate.getMaterial();
                if (fluidstate.is(FluidTags.WATER)) {
                    if (blockstate.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)blockstate.getBlock()).takeLiquid(p_176312_1_, blockpos1, blockstate) != Fluids.EMPTY) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (blockstate.getBlock() instanceof FlowingFluidBlock) {
                        p_176312_1_.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) {
                        TileEntity tileentity = blockstate.hasTileEntity() ? p_176312_1_.getBlockEntity(blockpos1) : null;
                        dropResources(blockstate, p_176312_1_, blockpos1, tileentity);
                        p_176312_1_.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }
}
