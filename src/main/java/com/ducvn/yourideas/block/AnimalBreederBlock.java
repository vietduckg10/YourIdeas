package com.ducvn.yourideas.block;

import com.ducvn.yourideas.config.YourIdeasConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class AnimalBreederBlock extends Block{

    public AnimalBreederBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    private static boolean trigger = false;

    private static final VoxelShape SHAPE =
            Block.box(4, 0, 4, 12, 8, 12);

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random randomSource) {
        if (YourIdeasConfig.animal_breeder.get()){
            AxisAlignedBB aaBB = new AxisAlignedBB(pos.offset(-3, 0, -3), pos.offset(3, 3, 3));
            List<AnimalEntity> animalList = world.getEntitiesOfClass(AnimalEntity.class, aaBB);
            if (animalList.size() < 50) {
                for (AnimalEntity animal : animalList) {
                    if (animal.getAge() == 0) {
                        animal.setInLove(null);
                    }
                }
                trigger = true;
            }
        }
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random randomSource) {
        if (trigger){
            world.addParticle(ParticleTypes.HEART,
                    (pos.getX() + 0.2D) + (randomSource.nextDouble() * 0.6D),
                    (pos.getY() + 0.5D) + (randomSource.nextDouble() * 0.5D),
                    (pos.getZ() + 0.2D) + (randomSource.nextDouble() * 0.6D),
                    0D, 0.1D, 0D);
        }
        trigger = false;
        super.animateTick(state, world, pos, randomSource);
    }
}
