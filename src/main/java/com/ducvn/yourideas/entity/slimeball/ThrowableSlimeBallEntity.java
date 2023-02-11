package com.ducvn.yourideas.entity.slimeball;

import com.ducvn.yourideas.block.SlimeSplashBlock;
import com.ducvn.yourideas.block.YourIdeasBlocksRegister;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.entity.YourIdeasEntitiesRegister;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class ThrowableSlimeBallEntity extends ProjectileItemEntity {
    public ThrowableSlimeBallEntity(EntityType<? extends ProjectileItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrowableSlimeBallEntity(double x, double y, double z, World world) {
        super(YourIdeasEntitiesRegister.THROWABLE_SLIME_BALL.get(), x, y, z, world);
    }

    public ThrowableSlimeBallEntity(LivingEntity entity, World world) {
        super(YourIdeasEntitiesRegister.THROWABLE_SLIME_BALL.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL.asItem();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide && YourIdeasConfig.throwable_slimeball.get()){
            if (traceResult.getEntity() instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) traceResult.getEntity();
                DamageSource damageSource = DamageSource.playerAttack((PlayerEntity) this.getOwner());
                livingEntity.hurt(damageSource, 1.0F);
                livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0));
                this.remove();
            }
            else {
                this.remove();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult traceResult) {
        if (!level.isClientSide && YourIdeasConfig.throwable_slimeball.get()){
            if (RayTraceResult.Type.BLOCK == traceResult.getType()){
                Block hitBlock = level.getBlockState(traceResult.getBlockPos()).getBlock();
                if (hitBlock.defaultBlockState().getMaterial().isSolid() && !(hitBlock instanceof SlimeSplashBlock)){
                    BlockPos slimeBallPos;
                    switch (traceResult.getDirection()){
                        case EAST:
                            slimeBallPos = traceResult.getBlockPos().east();
                            break;
                        case WEST:
                            slimeBallPos = traceResult.getBlockPos().west();
                            break;
                        case NORTH:
                            slimeBallPos = traceResult.getBlockPos().north();
                            break;
                        case SOUTH:
                            slimeBallPos = traceResult.getBlockPos().south();
                            break;
                        case UP:
                        case DOWN:
                        default:
                            slimeBallPos = null;
                            break;
                    }
                    if (slimeBallPos != null){
                        World world = this.level;
                        world.setBlock(slimeBallPos, YourIdeasBlocksRegister.SLIME_SPLASH_BLOCK.get().defaultBlockState()
                                .setValue(SlimeSplashBlock.FACING, traceResult.getDirection())
                                        .setValue(SlimeSplashBlock.VARIANT, new Random().nextInt(3) + 1)
                                , 3);
                    }
                    this.remove();
                }
                else {
                    this.remove();
                }
            }
        }
    }
}
